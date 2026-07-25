from __future__ import annotations

ORDERS: dict[str, dict] = {}
OUTBOX: list[dict] = []


def write_order_and_outbox(order: dict) -> dict:
	ORDERS[order["orderId"]] = order
	message = {
		"eventType": "OrderUpdated",
		"orderId": order["orderId"],
		"status": order["status"],
	}
	OUTBOX.append(message)
	return {
		"order": ORDERS[order["orderId"]],
		"outboxMessage": message,
	}


def publish_outbox() -> list[dict]:
	published = list(OUTBOX)
	OUTBOX.clear()
	return published
