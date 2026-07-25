from __future__ import annotations

import json


def response(status_code: int, body: dict) -> dict:
	return {
		"statusCode": status_code,
		"headers": {"Content-Type": "application/json"},
		"body": json.dumps(body),
	}


def parse_json_body(body: str | None) -> dict:
	if not body:
		return {}
	return json.loads(body)


def handler(event, context):
	method = event.get("httpMethod")
	path = event.get("path")

	if method == "GET" and path == "/health":
		return response(200, {"status": "ok"})

	if method == "POST" and path == "/orders":
		payload = parse_json_body(event.get("body"))
		order_id = payload.get("orderId")
		if not order_id:
			return response(400, {"error": "orderId is required"})
		print(f"accepted order: {order_id}")
		return response(202, {"accepted": True, "orderId": order_id})

	return response(404, {"error": "not found"})