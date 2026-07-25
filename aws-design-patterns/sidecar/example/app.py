from __future__ import annotations


def handle_request(path: str) -> dict:
	return {
		"path": path,
		"status": 200,
		"body": f"served {path}",
	}
