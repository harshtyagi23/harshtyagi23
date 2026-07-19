from __future__ import annotations

import json
from typing import Any


def enqueue_job(sqs_client: Any, queue_url: str, job_id: str) -> None:
	payload = json.dumps({"jobId": job_id, "priority": "normal"})
	sqs_client.send_message(QueueUrl=queue_url, MessageBody=payload)


if __name__ == "__main__":
	print("Use enqueue_job(...) from a script or service.")