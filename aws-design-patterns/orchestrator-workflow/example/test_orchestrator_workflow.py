from __future__ import annotations

from orchestrator import run_workflow
from producer import make_order_requested_event


def test_workflow_success() -> None:
	event = make_order_requested_event("order-101", 149.99)
	result = run_workflow(event)

	assert result["status"] == "SUCCEEDED"
	assert result["orderId"] == "order-101"
	assert result["shipmentId"] == "ship-order-101"


def test_workflow_fails_on_inventory() -> None:
	event = make_order_requested_event(
		"order-102",
		59.0,
		inventory_available=False,
	)
	result = run_workflow(event)

	assert result["status"] == "FAILED"
	assert result["stage"] == "reserveInventory"
	assert result["reason"] == "inventory unavailable"


def test_workflow_compensates_on_payment_failure() -> None:
	event = make_order_requested_event(
		"order-103",
		249.0,
		payment_authorized=False,
	)
	result = run_workflow(event)

	assert result["status"] == "FAILED"
	assert result["stage"] == "chargePayment"
	assert result["reason"] == "payment declined"
	assert result["compensation"]["status"] == "COMPENSATED"
	assert result["compensation"]["stage"] == "releaseInventory"


def test_workflow_fails_on_invalid_input() -> None:
	result = run_workflow({"orderId": "order-104"})

	assert result["status"] == "FAILED"
	assert result["stage"] == "validate"
	assert "missing keys" in result["reason"]
