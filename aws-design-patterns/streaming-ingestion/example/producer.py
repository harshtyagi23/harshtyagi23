from __future__ import annotations


def make_click_event(user_id: str, page: str) -> dict:
	return {
		"userId": user_id,
		"page": page,
		"eventType": "page-view",
	}
