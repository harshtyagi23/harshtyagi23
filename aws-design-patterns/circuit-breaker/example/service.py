from __future__ import annotations

from typing import Any, Callable

from circuit_breaker import CircuitBreaker, CircuitOpenError


def process_with_circuit_breaker(
    breaker: CircuitBreaker,
    dependency: Callable[[dict[str, Any]], dict[str, Any]],
    payload: dict[str, Any],
    now: int,
) -> dict[str, Any]:
    if not breaker.allow_request(now):
        raise CircuitOpenError("circuit-open")

    try:
        result = dependency(payload)
        breaker.on_success()
        return {"status": "ok", "breakerState": breaker.state, "result": result}
    except Exception as exc:  # noqa: BLE001
        breaker.on_failure(now)
        return {
            "status": "failed",
            "breakerState": breaker.state,
            "error": str(exc),
        }
