from __future__ import annotations

from retry_policy import NonRetryableError, RetryConfig, RetryExecutor, RetryableError


def test_success_without_retries() -> None:
    executor = RetryExecutor(RetryConfig(seed=1))

    result = executor.run(lambda: {"accepted": True})

    assert result["status"] == "ok"
    assert result["attempts"] == 1
    assert result["events"] == []


def test_success_after_transient_failures() -> None:
    executor = RetryExecutor(RetryConfig(max_attempts=5, seed=3))
    state = {"attempt": 0}

    def op() -> dict[str, bool]:
        state["attempt"] += 1
        if state["attempt"] < 3:
            raise RetryableError("throttled")
        return {"accepted": True}

    result = executor.run(op)

    assert result["status"] == "ok"
    assert result["attempts"] == 3
    assert len(result["events"]) == 2
    assert result["events"][0]["baseDelayMs"] == 100
    assert result["events"][1]["baseDelayMs"] == 200


def test_exhausts_retries() -> None:
    executor = RetryExecutor(RetryConfig(max_attempts=3, seed=2))

    result = executor.run(lambda: (_ for _ in ()).throw(RetryableError("timeout")))

    assert result["status"] == "failed"
    assert result["attempts"] == 3
    assert len(result["events"]) == 2


def test_stops_on_non_retryable_error() -> None:
    executor = RetryExecutor(RetryConfig(max_attempts=5, seed=2))

    result = executor.run(lambda: (_ for _ in ()).throw(NonRetryableError("bad-request")))

    assert result["status"] == "failed"
    assert result["attempts"] == 1
    assert result["events"] == []
