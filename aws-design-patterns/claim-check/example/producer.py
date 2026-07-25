from __future__ import annotations

import json
from typing import Any


def publish_claim_check(
	s3_client: Any,
	sqs_client: Any,
	bucket: str,
	queue_url: str,
	order_id: str,
	payload: dict,
) -> None:
	key = f"orders/{order_id}.json"
	s3_client.put_object(Bucket=bucket, Key=key, Body=json.dumps(payload).encode("utf-8"))
	message = {"orderId": order_id, "bucket": bucket, "key": key}
	sqs_client.send_message(QueueUrl=queue_url, MessageBody=json.dumps(message))


if __name__ == "__main__":
	print("Use publish_claim_check(...) from a script or service.")