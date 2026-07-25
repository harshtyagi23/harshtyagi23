from __future__ import annotations

from consumer_a import handler as consumer_a_handler
from consumer_b import handler as consumer_b_handler
from publisher import publish_event


class FakeSnsClient:
    def __init__(self) -> None:
        self.calls = []

    def publish(self, **kwargs):
        self.calls.append(kwargs)


def test_publish_event_sends_expected_message() -> None:
    client = FakeSnsClient()

    publish_event(client, "topic-arn", "order-123")

    assert client.calls == [
        {
            "TopicArn": "topic-arn",
            "Message": '{"orderId": "order-123", "eventType": "OrderCreated"}',
            "Subject": "OrderCreated",
        }
    ]


def test_consumer_a_processes_all_records(capsys) -> None:
    event = {"Records": [{"body": "payload-a"}]}

    result = consumer_a_handler(event, None)

    assert result == {"statusCode": 200, "body": "processed by consumer A"}
    assert "queue-a received: payload-a" in capsys.readouterr().out


def test_consumer_b_processes_all_records(capsys) -> None:
    event = {"Records": [{"body": "payload-b"}]}

    result = consumer_b_handler(event, None)

    assert result == {"statusCode": 200, "body": "processed by consumer B"}
    assert "queue-b received: payload-b" in capsys.readouterr().out