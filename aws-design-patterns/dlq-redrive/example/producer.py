from __future__ import annotations

import json
from typing import Any


def enqueue_work(sqs_client: Any, queue_url: str, job_id: str, force_fail: bool = False) -> None:
	payload = {"jobId": job_id, "action": "process-report"}
	if force_fail:
		payload["forceFail"] = True
	sqs_client.send_message(QueueUrl=queue_url, MessageBody=json.dumps(payload))


if __name__ == "__main__":
	print("Use enqueue_work(...) from a script or service.")