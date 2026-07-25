from __future__ import annotations

import base64
import json

from lambda_handler import handler
from producer import make_click_event
from stream_processor import process_batch


def test_process_batch_counts_pages() -> None:
	events = [
		make_click_event("u-1", "/Home"),
		make_click_event("u-2", "/home"),
		make_click_event("u-3", "/pricing"),
	]
	result = process_batch(events)

	assert result["recordCount"] == 3
	assert result["pageCounts"]["/home"] == 2
	assert result["pageCounts"]["/pricing"] == 1


def test_lambda_handler_decodes_kinesis_batch() -> None:
	event = make_click_event("u-4", "/Docs")
	encoded = base64.b64encode(json.dumps(event).encode("utf-8")).decode("utf-8")
	lambda_event = {
		"Records": [
			{"kinesis": {"data": encoded}},
		],
	}
	result = handler(lambda_event, None)

	assert result["recordCount"] == 1
	assert result["pageCounts"]["/docs"] == 1
