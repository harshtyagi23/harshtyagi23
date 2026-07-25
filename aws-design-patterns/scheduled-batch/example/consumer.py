from __future__ import annotations


DATA_SOURCE = [
	{"id": "task-1", "status": "PENDING"},
	{"id": "task-2", "status": "DONE"},
	{"id": "task-3", "status": "PENDING"},
	{"id": "task-4", "status": "PENDING"},
	{"id": "task-5", "status": "PENDING"},
]


def select_pending(records: list[dict], max_items: int) -> list[dict]:
	return [record for record in records if record.get("status") == "PENDING"][:max_items]


def handler(event, context):
	batch = select_pending(DATA_SOURCE, max_items=3)
	processed_ids = [item["id"] for item in batch]
	print(f"scheduled run processed: {processed_ids}")
	return {
		"processedIds": processed_ids,
		"count": len(processed_ids),
		"triggerTime": event.get("time"),
	}