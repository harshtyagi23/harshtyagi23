from __future__ import annotations

import pytest

from consumer import handler
from producer import enqueue_work


class FakeSqsClient:
	def __init__(self) -> None:
		self.calls = []

	def send_message(self, **kwargs):
		self.calls.append(kwargs)


def test_enqueue_work_sends_expected_message() -> None:
	client = FakeSqsClient()

	enqueue_work(client, "https://example.com/queue", "job-123", force_fail=True)

	assert client.calls == [
		{
			"QueueUrl": "https://example.com/queue",
			"MessageBody": '{"jobId": "job-123", "action": "process-report", "forceFail": true}',
		}
	]


def test_handler_processes_non_failing_messages(capsys) -> None:
	event = {"Records": [{"body": '{"jobId": "job-123", "action": "process-report"}'}]}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert "processed job: job-123" in capsys.readouterr().out


def test_handler_raises_for_forced_failure() -> None:
	event = {"Records": [{"body": '{"jobId": "job-123", "action": "process-report", "forceFail": true}'}]}

	with pytest.raises(ValueError, match="forced failure for DLQ demo"):
		handler(event, None)