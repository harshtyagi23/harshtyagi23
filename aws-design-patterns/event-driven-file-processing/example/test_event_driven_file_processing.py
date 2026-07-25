from __future__ import annotations

from producer import upload_file
from consumer import handler


class FakeS3PutClient:
	def __init__(self) -> None:
		self.calls = []

	def put_object(self, **kwargs):
		self.calls.append(kwargs)


class FakeBody:
	def __init__(self, text: str) -> None:
		self._text = text

	def read(self) -> bytes:
		return self._text.encode("utf-8")


class FakeS3GetClient:
	def __init__(self) -> None:
		self.calls = []

	def get_object(self, **kwargs):
		self.calls.append(kwargs)
		return {"Body": FakeBody("hello world")}


def test_upload_file_sends_expected_object() -> None:
	client = FakeS3PutClient()

	upload_file(client, "uploads-bucket", "incoming/report-1.csv", "hello")

	assert client.calls == [
		{
			"Bucket": "uploads-bucket",
			"Key": "incoming/report-1.csv",
			"Body": b"hello",
		}
	]


def test_handler_processes_s3_event(capsys, monkeypatch) -> None:
	fake = FakeS3GetClient()
	monkeypatch.setattr("consumer.s3_client", fake)
	event = {
		"Records": [
			{
				"s3": {
					"bucket": {"name": "uploads-bucket"},
					"object": {"key": "incoming/report-1.csv"},
				}
			}
		]
	}

	result = handler(event, None)

	assert result == {"statusCode": 200, "body": "processed"}
	assert fake.calls == [{"Bucket": "uploads-bucket", "Key": "incoming/report-1.csv"}]
	assert "processed file: incoming/report-1.csv, bytes=11" in capsys.readouterr().out