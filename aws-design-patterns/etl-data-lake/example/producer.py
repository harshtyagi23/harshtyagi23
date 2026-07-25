from __future__ import annotations


def make_raw_record(order_id: str, amount: float, country: str) -> dict:
	return {
		"order_id": order_id,
		"amount": amount,
		"country": country,
	}
