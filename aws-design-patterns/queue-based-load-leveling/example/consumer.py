from __future__ import annotations


def handler(event, context):
	for record in event.get("Records", []):
		print(f"processing queued work: {record['body']}")
	return {"statusCode": 200, "body": "processed"}