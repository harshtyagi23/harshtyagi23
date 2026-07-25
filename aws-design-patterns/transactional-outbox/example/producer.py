from __future__ import annotations


def make_order_record(order_id: str, status: str) -> dict:
	return {
		"orderId": order_id,
		"status": status,
	}
