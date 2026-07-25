from __future__ import annotations


def make_change_event(entity_id: str, operation: str, payload: dict) -> dict:
	return {
		"entityId": entity_id,
		"operation": operation,
		"payload": payload,
	}
