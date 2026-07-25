from __future__ import annotations


def normalize_event(event: dict) -> dict:
	return {
		"userId": event["userId"],
		"page": event["page"].lower(),
		"eventType": event["eventType"],
	}


def aggregate_counts(events: list[dict]) -> dict:
	counts: dict[str, int] = {}
	for event in events:
		page = event["page"]
		counts[page] = counts.get(page, 0) + 1
	return counts


def process_batch(events: list[dict]) -> dict:
	normalized = [normalize_event(event) for event in events]
	return {
		"recordCount": len(normalized),
		"pageCounts": aggregate_counts(normalized),
	}
