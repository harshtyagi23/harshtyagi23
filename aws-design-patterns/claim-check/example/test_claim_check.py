from __future__ import annotations

import io
import json

from consumer import handler
from producer import publish_claim_check


class FakeS3Client:
	def __init__(self) -> None:
		self.put_calls = []
		self.objects = {}

	def put_object(self, **kwargs):
		self.put_calls.append(kwargs)
		self.objects[(kwargs["Bucket"], kwargs["Key"])] = kwargs["Body"]

	def get_object(self, **kwargs):
		body = self.objects[(kwargs["Bucket"], kwargs["Key"])]
		return {"Body": io.BytesIO(body)}


class FakeSqsClient:
	def __init__(self) -> None:
		self.calls = []

	def send_message(self, **kwargs):
		self.calls.append(kwargs)


def test_publish_claim_check_stores_payload_and_sends_reference() -> None:
	s3_client = FakeS3Client()
	sqs_client = FakeSqsClient()

	payload = {"orderId": "order-123", "items": ["a", "b"]}
	publish_claim_check(
		s3_client=s3_client,
		sqs_client=sqs_client,
		bucket="claim-check-bucket",
		queue_url="https://example.com/queue",
		order_id="order-123",
		payload=payload,
	)

	assert s3_client.put_calls[0]["Bucket"] == "claim-check-bucket"
	assert s3_client.put_calls[0]["Key"] == "orders/order-123.json"
	assert sqs_client.calls == [
		{
			"QueueUrl": "https://example.com/queue",
			"MessageBody": '{"orderId": "order-123", "bucket": "claim-check-bucket", "key": "orders/order-123.json"}',
		}
	]


def test_handler_reads_payload_using_reference(capsys, monkeypatch) -> None:
	s3_client = FakeS3Client()
	payload = {"orderId": "order-123", "items": ["a", "b"]}
	s3_client.put_object(Bucket="claim-check-bucket", Key="orders/order-123.json", Body=json.dumps(payload).encode("utf-8"))

	monkeypatch.setattr("consumer.s3_client", s3_client)
	event = {
		"Records": [
			{"body": '{"orderId": "order-123", "bucket": "claim-check-bucket", "key": "orders/order-123.json"}'}
		]
	}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert "processed claim-check payload for order-123" in capsys.readouterr().out