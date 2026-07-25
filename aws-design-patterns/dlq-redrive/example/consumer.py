from __future__ import annotations

import json


def handler(event, context):
	for record in event.get("Records", []):
		body = json.loads(record["body"])
		if body.get("forceFail"):
			raise ValueError("forced failure for DLQ demo")
		print(f"processed job: {body['jobId']}")
	return {"statusCode": 200, "body": "processed"}