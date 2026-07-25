from __future__ import annotations

import json

from etl import run_etl


def handler(event, context):
	raw_records = event.get("records", [])
	result = run_etl(raw_records)
	print(json.dumps(result))
	return result
