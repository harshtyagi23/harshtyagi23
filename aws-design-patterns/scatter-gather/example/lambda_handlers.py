from __future__ import annotations

from aggregator import gather
from services import catalog_service, pricing_service, reviews_service


def catalog_handler(event, context):
	return catalog_service(event)


def reviews_handler(event, context):
	return reviews_service(event)


def pricing_handler(event, context):
	return pricing_service(event)


def gather_handler(event, context):
	return gather(event)
