from __future__ import annotations


class _S3ClientPlaceholder:
	def get_object(self, **kwargs):
		raise RuntimeError("s3_client must be configured or monkeypatched in tests")


s3_client = _S3ClientPlaceholder()


def handler(event, context):
	for record in event.get("Records", []):
		bucket = record["s3"]["bucket"]["name"]
		key = record["s3"]["object"]["key"]
		obj = s3_client.get_object(Bucket=bucket, Key=key)
		text = obj["Body"].read().decode("utf-8")
		print(f"processed file: {key}, bytes={len(text)}")
	return {"statusCode": 200, "body": "processed"}