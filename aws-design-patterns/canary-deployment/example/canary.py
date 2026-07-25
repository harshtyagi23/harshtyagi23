from __future__ import annotations

from dataclasses import dataclass


@dataclass
class CanaryPolicy:
    current_weight: int = 10
    step: int = 40
    max_error_rate: float = 0.02


def evaluate_canary(error_rate: float, latency_p95_ms: int, latency_budget_ms: int) -> dict[str, int | str]:
    if error_rate > 0.02:
        return {"status": "rollback", "reason": "error-rate-alarm"}
    if latency_p95_ms > latency_budget_ms:
        return {"status": "rollback", "reason": "latency-alarm"}
    return {"status": "healthy"}


def next_stage(policy: CanaryPolicy, health: dict[str, int | str]) -> dict[str, int | str]:
    if health["status"] != "healthy":
        return {"status": "rollback", "reason": str(health.get("reason", "unknown"))}

    next_weight = min(100, policy.current_weight + policy.step)
    if next_weight == 100:
        return {"status": "promote", "nextWeight": 100}

    return {"status": "continue", "nextWeight": next_weight}
