from __future__ import annotations

from choreography import dispatch_event
from producer import make_order_created_event


def test_event_shape() -> None:
	event = make_order_created_event("order-201", 75.5)

	assert event["source"] == "app.orders"
	assert event["detail-type"] == "OrderCreated"
	assert event["detail"]["orderId"] == "order-201"


def test_dispatch_reaches_all_consumers() -> None:
	event = make_order_created_event("order-202", 99.0)
	results = dispatch_event(event)

	assert len(results) == 3
	assert {result["service"] for result in results} == {
		"inventory",
		"billing",
		"notification",
	}


def test_billing_receives_amount() -> None:
	event = make_order_created_event("order-203", 149.99)
	results = dispatch_event(event)
	billing = next(result for result in results if result["service"] == "billing")

	assert billing["amount"] == 149.99
	assert billing["status"] == "INVOICED"
