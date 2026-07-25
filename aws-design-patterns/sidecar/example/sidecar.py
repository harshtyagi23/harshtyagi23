from __future__ import annotations


def enrich_log(record: dict) -> dict:
	return {
		"service": "log-sidecar",
		"path": record["path"],
		"status": record["status"],
		"message": f"forwarded log for {record['path']}",
	}


def auth_proxy(headers: dict) -> dict:
	token = headers.get("authorization")
	return {
		"authorized": bool(token),
		"reason": "token present" if token else "missing token",
	}
