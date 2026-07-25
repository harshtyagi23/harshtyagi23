from __future__ import annotations


def make_order_created_event(order_id: str, total_amount: float) -> dict:
	return {
		"source": "app.orders",
		"detail-type": "OrderCreated",
		"detail": {
			"orderId": order_id,
			"totalAmount": float(total_amount),
		},
	}
