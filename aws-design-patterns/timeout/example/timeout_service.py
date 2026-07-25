from __future__ import annotations

from dataclasses import dataclass
from typing import Any


@dataclass(frozen=True)
class TimeoutPolicy:
    timeout_ms: int
    fallback_value: str = "cached-response"


class SimulatedDependency:
    def call(self, payload: dict[str, Any], latency_ms: int) -> dict[str, Any]:
        return {
            "profileId": payload.get("profileId", "unknown"),
            "latencyMs": latency_ms,
        }


def execute_with_timeout(
    dependency: SimulatedDependency,
    payload: dict[str, Any],
    latency_ms: int,
    policy: TimeoutPolicy,
) -> dict[str, Any]:
    if latency_ms > policy.timeout_ms:
        return {
            "status": "timeout",
            "elapsedMs": latency_ms,
            "fallback": policy.fallback_value,
        }

    result = dependency.call(payload, latency_ms)
    return {
        "status": "ok",
        "elapsedMs": latency_ms,
        "result": result,
    }
