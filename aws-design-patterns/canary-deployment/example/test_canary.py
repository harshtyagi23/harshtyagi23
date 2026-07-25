from __future__ import annotations

from canary import CanaryPolicy, evaluate_canary, next_stage


def test_healthy_canary_continues_or_promotes() -> None:
    health = evaluate_canary(error_rate=0.01, latency_p95_ms=120, latency_budget_ms=200)
    policy = CanaryPolicy(current_weight=10, step=40)

    result = next_stage(policy, health)

    assert result["status"] == "continue"
    assert result["nextWeight"] == 50


def test_high_error_rate_rolls_back() -> None:
    health = evaluate_canary(error_rate=0.03, latency_p95_ms=120, latency_budget_ms=200)
    policy = CanaryPolicy(current_weight=10, step=40)

    result = next_stage(policy, health)

    assert result["status"] == "rollback"
    assert result["reason"] == "error-rate-alarm"


def test_high_latency_rolls_back() -> None:
    health = evaluate_canary(error_rate=0.01, latency_p95_ms=260, latency_budget_ms=200)
    policy = CanaryPolicy(current_weight=10, step=40)

    result = next_stage(policy, health)

    assert result["status"] == "rollback"
    assert result["reason"] == "latency-alarm"


def test_reaches_full_promotion() -> None:
    health = {"status": "healthy"}
    policy = CanaryPolicy(current_weight=70, step=40)

    result = next_stage(policy, health)

    assert result["status"] == "promote"
    assert result["nextWeight"] == 100
