from __future__ import annotations

from consumer import handler
from producer import enqueue_work


class FakeSqsClient:
	def __init__(self) -> None:
		self.calls = []

	def send_message(self, **kwargs):
		self.calls.append(kwargs)


def test_enqueue_work_sends_expected_message() -> None:
	client = FakeSqsClient()

	enqueue_work(client, "https://example.com/queue", "order-123")

	assert client.calls == [
		{
			"QueueUrl": "https://example.com/queue",
			"MessageBody": '{"orderId": "order-123", "priority": "normal"}',
		}
	]


def test_handler_processes_all_records(capsys) -> None:
	event = {"Records": [{"body": "payload-1"}]}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert "processing queued work: payload-1" in capsys.readouterr().out