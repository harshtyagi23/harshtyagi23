from __future__ import annotations

from blue_green import DeploymentState, deploy_and_cutover, validate_green


def test_validation_requires_all_checks() -> None:
    assert validate_green(True, True) is True
    assert validate_green(True, False) is False
    assert validate_green(False, True) is False


def test_cutover_switches_to_green_on_success() -> None:
    state = DeploymentState()

    result = deploy_and_cutover(state, health_checks_passed=True, smoke_tests_passed=True)

    assert result["status"] == "cutover-complete"
    assert result["from"] == "blue"
    assert result["to"] == "green"
    assert state.active == "green"


def test_failed_validation_keeps_blue_active() -> None:
    state = DeploymentState()

    result = deploy_and_cutover(state, health_checks_passed=True, smoke_tests_passed=False)

    assert result["status"] == "rollback"
    assert result["active"] == "blue"
    assert result["reason"] == "health-check-failed"
    assert state.active == "blue"
