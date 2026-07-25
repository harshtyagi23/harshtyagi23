from __future__ import annotations

import json

from consumer import handler
from producer import enqueue_event


class FakeSqsClient:
	def __init__(self) -> None:
		self.calls = []

	def send_message(self, **kwargs):
		self.calls.append(kwargs)


class FakeDdbClient:
	class exceptions:
		class ConditionalCheckFailedException(Exception):
			pass

	def __init__(self, duplicate: bool = False) -> None:
		self.calls = []
		self.duplicate = duplicate

	def put_item(self, **kwargs):
		self.calls.append(kwargs)
		if self.duplicate:
			raise self.exceptions.ConditionalCheckFailedException()


def test_enqueue_event_sends_expected_message() -> None:
	client = FakeSqsClient()

	enqueue_event(client, "https://example.com/queue", "order-123")

	assert client.calls == [
		{
			"QueueUrl": "https://example.com/queue",
			"MessageBody": '{"orderId": "order-123", "eventType": "OrderCreated"}',
		}
	]


def test_handler_processes_new_event(capsys, monkeypatch) -> None:
	ddb = FakeDdbClient()
	monkeypatch.setattr("consumer.ddb_client", ddb)
	event = {"Records": [{"body": json.dumps({"orderId": "order-123", "eventType": "OrderCreated"})}]}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert ddb.calls[0]["ConditionExpression"] == "attribute_not_exists(idempotencyKey)"
	assert "processed once: order-123" in capsys.readouterr().out


def test_handler_ignores_duplicate_event(capsys, monkeypatch) -> None:
	ddb = FakeDdbClient(duplicate=True)
	monkeypatch.setattr("consumer.ddb_client", ddb)
	event = {"Records": [{"body": json.dumps({"orderId": "order-123", "eventType": "OrderCreated"})}]}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert "duplicate ignored: order-123" in capsys.readouterr().out