from __future__ import annotations

from read_model import project_to_search_index, search_products, update_write_model


def write_handler(event, context):
	stored = update_write_model(event)
	projection = project_to_search_index(event)
	return {
		"writeModel": stored,
		"projection": projection,
	}


def search_handler(event, context):
	query = event.get("query", "")
	return {
		"query": query,
		"results": search_products(query),
	}
