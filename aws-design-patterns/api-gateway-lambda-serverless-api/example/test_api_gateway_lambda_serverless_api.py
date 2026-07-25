from __future__ import annotations

import json

from producer import make_api_event
from consumer import handler


def test_health_endpoint() -> None:
	event = make_api_event("GET", "/health")

	result = handler(event, None)

	assert result["statusCode"] == 200
	assert json.loads(result["body"]) == {"status": "ok"}


def test_orders_endpoint_accepts_order(capsys) -> None:
	event = make_api_event("POST", "/orders", {"orderId": "order-123"})

	result = handler(event, None)

	assert result["statusCode"] == 202
	assert json.loads(result["body"]) == {"accepted": True, "orderId": "order-123"}
	assert "accepted order: order-123" in capsys.readouterr().out


def test_orders_endpoint_validates_order_id() -> None:
	event = make_api_event("POST", "/orders", {})

	result = handler(event, None)

	assert result["statusCode"] == 400
	assert json.loads(result["body"]) == {"error": "orderId is required"}


def test_unknown_route_returns_404() -> None:
	event = make_api_event("GET", "/unknown")

	result = handler(event, None)

	assert result["statusCode"] == 404