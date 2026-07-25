from __future__ import annotations

from outbox import publish_outbox, write_order_and_outbox


def write_handler(event, context):
	return write_order_and_outbox(event)


def publisher_handler(event, context):
	return {
		"published": publish_outbox(),
	}
