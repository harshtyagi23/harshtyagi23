from __future__ import annotations

from timeout_service import SimulatedDependency, TimeoutPolicy, execute_with_timeout


def test_returns_success_within_timeout() -> None:
    policy = TimeoutPolicy(timeout_ms=250)
    dep = SimulatedDependency()

    response = execute_with_timeout(dep, {"profileId": "u-1"}, latency_ms=100, policy=policy)

    assert response["status"] == "ok"
    assert response["elapsedMs"] == 100
    assert response["result"]["profileId"] == "u-1"


def test_returns_timeout_when_latency_exceeds_budget() -> None:
    policy = TimeoutPolicy(timeout_ms=250, fallback_value="cached-profile")
    dep = SimulatedDependency()

    response = execute_with_timeout(dep, {"profileId": "u-2"}, latency_ms=260, policy=policy)

    assert response["status"] == "timeout"
    assert response["elapsedMs"] == 260
    assert response["fallback"] == "cached-profile"


def test_timeout_boundary_inclusive_success() -> None:
    policy = TimeoutPolicy(timeout_ms=250)
    dep = SimulatedDependency()

    response = execute_with_timeout(dep, {"profileId": "u-3"}, latency_ms=250, policy=policy)

    assert response["status"] == "ok"
    assert response["elapsedMs"] == 250
