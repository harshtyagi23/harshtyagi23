from __future__ import annotations

from handlers import search_handler, write_handler
from producer import make_product_updated_event
from read_model import SEARCH_INDEX, WRITE_STORE, search_products


def setup_function() -> None:
	WRITE_STORE.clear()
	SEARCH_INDEX.clear()


def test_write_updates_write_model_and_projection() -> None:
	event = make_product_updated_event("p-101", "Gaming Laptop", "Electronics")
	result = write_handler(event, None)

	assert result["writeModel"]["productId"] == "p-101"
	assert result["projection"]["searchText"] == "gaming laptop electronics"


def test_search_reads_from_projection() -> None:
	write_handler(make_product_updated_event("p-102", "Mirrorless Camera", "Photography"), None)
	write_handler(make_product_updated_event("p-103", "Tripod", "Photography"), None)

	results = search_products("camera")

	assert len(results) == 1
	assert results[0]["productId"] == "p-102"


def test_search_handler_returns_query_and_results() -> None:
	write_handler(make_product_updated_event("p-104", "Coffee Grinder", "Kitchen"), None)
	result = search_handler({"query": "coffee"}, None)

	assert result["query"] == "coffee"
	assert result["results"][0]["productId"] == "p-104"
