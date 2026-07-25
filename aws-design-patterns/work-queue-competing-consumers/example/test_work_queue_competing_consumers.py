from __future__ import annotations

from producer import enqueue_job
from worker import handler


class FakeSqsClient:
	def __init__(self) -> None:
		self.calls = []

	def send_message(self, **kwargs):
		self.calls.append(kwargs)


def test_enqueue_job_sends_expected_message() -> None:
	client = FakeSqsClient()

	enqueue_job(client, "https://example.com/queue", "job-123")

	assert client.calls == [
		{
			"QueueUrl": "https://example.com/queue",
			"MessageBody": '{"jobId": "job-123", "priority": "normal"}',
		}
	]


def test_handler_processes_all_records(capsys) -> None:
	event = {"Records": [{"body": "payload-1"}]}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert "worker processed: payload-1" in capsys.readouterr().out