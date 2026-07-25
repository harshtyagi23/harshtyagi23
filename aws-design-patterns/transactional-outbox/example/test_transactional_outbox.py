from __future__ import annotations

from handlers import publisher_handler, write_handler
from outbox import ORDERS, OUTBOX
from producer import make_order_record


def setup_function() -> None:
	ORDERS.clear()
	OUTBOX.clear()


def test_write_persists_order_and_outbox_message() -> None:
	result = write_handler(make_order_record("order-401", "PAID"), None)

	assert result["order"]["orderId"] == "order-401"
	assert result["outboxMessage"]["eventType"] == "OrderUpdated"
	assert len(OUTBOX) == 1


def test_publisher_drains_outbox() -> None:
	write_handler(make_order_record("order-402", "SHIPPED"), None)
	published = publisher_handler({}, None)

	assert published["published"][0]["orderId"] == "order-402"
	assert OUTBOX == []


def test_order_state_remains_after_publish() -> None:
	write_handler(make_order_record("order-403", "CREATED"), None)
	publisher_handler({}, None)

	assert ORDERS["order-403"]["status"] == "CREATED"
