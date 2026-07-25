from __future__ import annotations

import json


class _S3ClientPlaceholder:
	def get_object(self, **kwargs):
		raise RuntimeError("s3_client must be configured or monkeypatched in tests")


s3_client = _S3ClientPlaceholder()


def handler(event, context):
	for record in event.get("Records", []):
		ref = json.loads(record["body"])
		obj = s3_client.get_object(Bucket=ref["bucket"], Key=ref["key"])
		payload = json.loads(obj["Body"].read().decode("utf-8"))
		print(f"processed claim-check payload for {payload['orderId']}")
	return {"statusCode": 200, "body": "processed"}