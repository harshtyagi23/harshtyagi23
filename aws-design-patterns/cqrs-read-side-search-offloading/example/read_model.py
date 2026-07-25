from __future__ import annotations

WRITE_STORE: dict[str, dict] = {}
SEARCH_INDEX: dict[str, dict] = {}


def update_write_model(event: dict) -> dict:
	WRITE_STORE[event["productId"]] = {
		"productId": event["productId"],
		"title": event["title"],
		"category": event["category"],
	}
	return WRITE_STORE[event["productId"]]


def project_to_search_index(event: dict) -> dict:
	SEARCH_INDEX[event["productId"]] = {
		"productId": event["productId"],
		"searchText": f"{event['title']} {event['category']}".lower(),
	}
	return SEARCH_INDEX[event["productId"]]


def search_products(query: str) -> list[dict]:
	query_text = query.lower()
	return [
		item
		for item in SEARCH_INDEX.values()
		if query_text in item["searchText"]
	]
