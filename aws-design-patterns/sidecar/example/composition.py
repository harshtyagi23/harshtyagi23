from __future__ import annotations

from app import handle_request
from sidecar import auth_proxy, enrich_log


def invoke_with_sidecar(path: str, headers: dict) -> dict:
	auth = auth_proxy(headers)
	if not auth["authorized"]:
		return {
			"status": 401,
			"reason": auth["reason"],
		}

	response = handle_request(path)
	log_record = enrich_log(response)
	return {
		"response": response,
		"sidecarLog": log_record,
	}
