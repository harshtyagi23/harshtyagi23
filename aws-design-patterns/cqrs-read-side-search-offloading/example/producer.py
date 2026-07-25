from __future__ import annotations


def make_product_updated_event(product_id: str, title: str, category: str) -> dict:
	return {
		"productId": product_id,
		"title": title,
		"category": category,
	}
