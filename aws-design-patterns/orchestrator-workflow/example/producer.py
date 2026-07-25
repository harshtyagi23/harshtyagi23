from __future__ import annotations


def make_order_requested_event(
	order_id: str,
	total_amount: float,
	currency: str = "USD",
	inventory_available: bool = True,
	payment_authorized: bool = True,
) -> dict:
	return {
		"orderId": order_id,
		"totalAmount": float(total_amount),
		"currency": currency,
		"inventoryAvailable": inventory_available,
		"paymentAuthorized": payment_authorized,
	}
