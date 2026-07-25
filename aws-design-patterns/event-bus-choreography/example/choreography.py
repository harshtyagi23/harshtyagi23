from __future__ import annotations

from consumers import billing_service, inventory_service, notification_service


def dispatch_event(event: dict) -> list[dict]:
	return [
		inventory_service(event),
		billing_service(event),
		notification_service(event),
	]
