from __future__ import annotations


def validate_order(event: dict) -> dict:
	required_keys = {"orderId", "totalAmount", "currency"}
	missing = sorted(required_keys - set(event.keys()))
	if missing:
		return {
			"status": "FAILED",
			"stage": "validate",
			"reason": f"missing keys: {', '.join(missing)}",
		}

	return {
		"status": "OK",
		"stage": "validate",
		"orderId": event["orderId"],
		"totalAmount": float(event["totalAmount"]),
		"currency": event["currency"],
		"inventoryAvailable": bool(event.get("inventoryAvailable", True)),
		"paymentAuthorized": bool(event.get("paymentAuthorized", True)),
	}


def reserve_inventory(order: dict) -> dict:
	if not order.get("inventoryAvailable", True):
		return {
			"status": "FAILED",
			"stage": "reserveInventory",
			"reason": "inventory unavailable",
			"orderId": order["orderId"],
		}

	return {
		"status": "OK",
		"stage": "reserveInventory",
		"reservationId": f"res-{order['orderId']}",
		"orderId": order["orderId"],
		"totalAmount": order["totalAmount"],
		"currency": order["currency"],
		"paymentAuthorized": order["paymentAuthorized"],
	}


def charge_payment(state: dict) -> dict:
	if not state.get("paymentAuthorized", True):
		return {
			"status": "FAILED",
			"stage": "chargePayment",
			"reason": "payment declined",
			"orderId": state["orderId"],
			"reservationId": state.get("reservationId"),
		}

	return {
		"status": "OK",
		"stage": "chargePayment",
		"paymentId": f"pay-{state['orderId']}",
		"orderId": state["orderId"],
		"reservationId": state.get("reservationId"),
	}


def ship_order(state: dict) -> dict:
	return {
		"status": "OK",
		"stage": "shipOrder",
		"shipmentId": f"ship-{state['orderId']}",
		"orderId": state["orderId"],
	}
