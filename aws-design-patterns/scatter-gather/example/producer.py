from __future__ import annotations


def make_search_request(query: str) -> dict:
	return {
		"requestId": "req-301",
		"query": query,
	}
