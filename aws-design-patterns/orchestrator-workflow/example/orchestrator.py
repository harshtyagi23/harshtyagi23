from __future__ import annotations

from workflow import charge_payment, reserve_inventory, ship_order, validate_order


def release_inventory(order_id: str, reservation_id: str | None) -> dict:
	return {
		"status": "COMPENSATED",
		"stage": "releaseInventory",
		"orderId": order_id,
		"reservationId": reservation_id,
	}


def run_workflow(event: dict) -> dict:
	validated = validate_order(event)
	if validated["status"] != "OK":
		return validated

	reserved = reserve_inventory(validated)
	if reserved["status"] != "OK":
		return reserved

	charged = charge_payment(reserved)
	if charged["status"] != "OK":
		compensation = release_inventory(
			order_id=charged["orderId"],
			reservation_id=charged.get("reservationId"),
		)
		return {
			"status": "FAILED",
			"stage": "chargePayment",
			"reason": charged["reason"],
			"orderId": charged["orderId"],
			"compensation": compensation,
		}

	shipped = ship_order(charged)
	return {
		"status": "SUCCEEDED",
		"orderId": shipped["orderId"],
		"shipmentId": shipped["shipmentId"],
	}
