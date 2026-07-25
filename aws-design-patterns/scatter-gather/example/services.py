from __future__ import annotations


def catalog_service(request: dict) -> dict:
	return {
		"source": "catalog",
		"results": [f"catalog:{request['query']}:a", f"catalog:{request['query']}:b"],
	}


def reviews_service(request: dict) -> dict:
	return {
		"source": "reviews",
		"results": [f"reviews:{request['query']}:top"],
	}


def pricing_service(request: dict) -> dict:
	return {
		"source": "pricing",
		"results": [f"pricing:{request['query']}:best-offer"],
	}
