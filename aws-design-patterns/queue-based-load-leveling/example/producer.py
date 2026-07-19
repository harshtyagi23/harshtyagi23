from __future__ import annotations

import json
from typing import Any


def enqueue_work(sqs_client: Any, queue_url: str, order_id: str) -> None:
	payload = json.dumps({"orderId": order_id, "priority": "normal"})
	sqs_client.send_message(QueueUrl=queue_url, MessageBody=payload)


if __name__ == "__main__":
	print("Use enqueue_work(...) from a script or service.")