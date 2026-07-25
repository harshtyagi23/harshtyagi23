from __future__ import annotations


def inventory_service(event: dict) -> dict:
	detail = event["detail"]
	return {
		"service": "inventory",
		"status": "RESERVED",
		"orderId": detail["orderId"],
	}


def billing_service(event: dict) -> dict:
	detail = event["detail"]
	return {
		"service": "billing",
		"status": "INVOICED",
		"orderId": detail["orderId"],
		"amount": detail["totalAmount"],
	}


def notification_service(event: dict) -> dict:
	detail = event["detail"]
	return {
		"service": "notification",
		"status": "CUSTOMER_NOTIFIED",
		"orderId": detail["orderId"],
	}
