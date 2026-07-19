from __future__ import annotations


def handler(event, context):
    for record in event.get("Records", []):
        print(f"queue-b received: {record['body']}")
    return {"statusCode": 200, "body": "processed by consumer B"}
