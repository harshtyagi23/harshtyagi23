from __future__ import annotations

from typing import Any


def upload_file(s3_client: Any, bucket: str, key: str, content: str) -> None:
	s3_client.put_object(Bucket=bucket, Key=key, Body=content.encode("utf-8"))


if __name__ == "__main__":
	print("Use upload_file(...) from a script or service.")