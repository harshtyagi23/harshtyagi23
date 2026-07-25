from __future__ import annotations

import base64
import json

from stream_processor import process_batch


def handler(event, context):
	records = []
	for record in event.get("Records", []):
		payload = base64.b64decode(record["kinesis"]["data"]).decode("utf-8")
		records.append(json.loads(payload))
	return process_batch(records)
