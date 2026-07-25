from __future__ import annotations

from consumers import billing_service, inventory_service, notification_service


def inventory_handler(event, context):
	return inventory_service(event)


def billing_handler(event, context):
	return billing_service(event)


def notification_handler(event, context):
	return notification_service(event)
