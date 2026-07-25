from __future__ import annotations

import json


def make_api_event(method: str, path: str, body: dict | None = None) -> dict:
	return {
		"httpMethod": method,
		"path": path,
		"body": json.dumps(body) if body is not None else None,
	}


if __name__ == "__main__":
	print(make_api_event("GET", "/health"))