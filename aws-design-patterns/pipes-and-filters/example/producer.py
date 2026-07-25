from __future__ import annotations


def make_input(order_id: str, amount: float, currency: str) -> dict:
	return {
		"orderId": order_id,
		"amount": amount,
		"currency": currency,
	}


if __name__ == "__main__":
	print(make_input("order-123", 149.99, "usd"))