from __future__ import annotations

from services import catalog_service, pricing_service, reviews_service


def scatter(request: dict) -> list[dict]:
	return [
		catalog_service(request),
		reviews_service(request),
		pricing_service(request),
	]


def gather(responses: list[dict]) -> dict:
	merged: list[str] = []
	for response in responses:
		merged.extend(response["results"])

	return {
		"sources": [response["source"] for response in responses],
		"results": merged,
		"resultCount": len(merged),
	}


def handle_request(request: dict) -> dict:
	responses = scatter(request)
	return gather(responses)
