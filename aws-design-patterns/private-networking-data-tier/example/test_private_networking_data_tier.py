from __future__ import annotations

from connectivity import can_reach_data_tier, describe_connection
from lambda_handler import handler
from producer import make_access_request


def test_private_subnet_with_security_group_access_is_allowed() -> None:
	assert can_reach_data_tier("private", True) is True


def test_public_path_is_blocked() -> None:
	result = describe_connection("public", True)
	assert result["allowed"] is False
	assert "blocked" in result["reason"]


def test_lambda_handler_returns_decision() -> None:
	event = make_access_request("private", True)
	result = handler(event, None)

	assert result["allowed"] is True
	assert result["reason"] == "private path allowed"
