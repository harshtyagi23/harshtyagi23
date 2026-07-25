from __future__ import annotations

import pytest

from circuit_breaker import CircuitBreaker, CircuitOpenError
from service import process_with_circuit_breaker


def always_fail(_: dict) -> dict:
    raise RuntimeError("downstream-unavailable")


def always_succeed(payload: dict) -> dict:
    return {"transactionId": f"txn-{payload['requestId']}"}


def test_closed_state_allows_success() -> None:
    breaker = CircuitBreaker(failure_threshold=2, recovery_timeout_seconds=10)

    result = process_with_circuit_breaker(
        breaker,
        always_succeed,
        {"requestId": "100"},
        now=1,
    )

    assert result["status"] == "ok"
    assert result["breakerState"] == "CLOSED"
    assert breaker.failure_count == 0


def test_opens_after_threshold_failures() -> None:
    breaker = CircuitBreaker(failure_threshold=2, recovery_timeout_seconds=10)

    first = process_with_circuit_breaker(breaker, always_fail, {"requestId": "1"}, now=1)
    second = process_with_circuit_breaker(breaker, always_fail, {"requestId": "2"}, now=2)

    assert first["status"] == "failed"
    assert second["status"] == "failed"
    assert second["breakerState"] == "OPEN"


def test_open_state_rejects_before_timeout() -> None:
    breaker = CircuitBreaker(failure_threshold=1, recovery_timeout_seconds=30)

    process_with_circuit_breaker(breaker, always_fail, {"requestId": "1"}, now=1)

    with pytest.raises(CircuitOpenError):
        process_with_circuit_breaker(breaker, always_succeed, {"requestId": "2"}, now=5)


def test_half_open_probe_recovery() -> None:
    breaker = CircuitBreaker(failure_threshold=1, recovery_timeout_seconds=10)

    process_with_circuit_breaker(breaker, always_fail, {"requestId": "1"}, now=1)

    result = process_with_circuit_breaker(
        breaker,
        always_succeed,
        {"requestId": "2"},
        now=20,
    )

    assert result["status"] == "ok"
    assert breaker.state == "CLOSED"
    assert breaker.failure_count == 0


def test_half_open_failure_reopens() -> None:
    breaker = CircuitBreaker(failure_threshold=1, recovery_timeout_seconds=10)

    process_with_circuit_breaker(breaker, always_fail, {"requestId": "1"}, now=1)

    result = process_with_circuit_breaker(
        breaker,
        always_fail,
        {"requestId": "2"},
        now=20,
    )

    assert result["status"] == "failed"
    assert breaker.state == "OPEN"
    assert breaker.opened_at == 20
