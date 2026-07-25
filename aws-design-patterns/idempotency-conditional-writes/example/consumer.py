from __future__ import annotations

import json


IDEMPOTENCY_TABLE = "IdempotencyTable"


class _DdbClientPlaceholder:
	class exceptions:
		class ConditionalCheckFailedException(Exception):
			pass

	def put_item(self, **kwargs):
		raise RuntimeError("ddb_client must be configured or monkeypatched in tests")


ddb_client = _DdbClientPlaceholder()


def handler(event, context):
	for record in event.get("Records", []):
		body = json.loads(record["body"])
		order_id = body["orderId"]
		try:
			ddb_client.put_item(
				TableName=IDEMPOTENCY_TABLE,
				Item={"idempotencyKey": {"S": order_id}},
				ConditionExpression="attribute_not_exists(idempotencyKey)",
			)
		except ddb_client.exceptions.ConditionalCheckFailedException:
			print(f"duplicate ignored: {order_id}")
			continue
		print(f"processed once: {order_id}")
	return {"statusCode": 200, "body": "processed"}