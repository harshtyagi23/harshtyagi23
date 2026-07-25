from __future__ import annotations

from dataclasses import dataclass


@dataclass
class DeploymentState:
    active: str = "blue"
    candidate: str = "green"


def validate_green(health_checks_passed: bool, smoke_tests_passed: bool) -> bool:
    return health_checks_passed and smoke_tests_passed


def deploy_and_cutover(
    state: DeploymentState,
    health_checks_passed: bool,
    smoke_tests_passed: bool,
) -> dict[str, str]:
    if not validate_green(health_checks_passed, smoke_tests_passed):
        return {
            "status": "rollback",
            "active": state.active,
            "reason": "health-check-failed",
        }

    previous = state.active
    state.active = state.candidate
    return {
        "status": "cutover-complete",
        "from": previous,
        "to": state.active,
    }
