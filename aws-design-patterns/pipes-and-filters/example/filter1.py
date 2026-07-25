from __future__ import annotations


def normalize(message: dict) -> dict:
	return {
		"orderId": message["orderId"],
		"amount": float(message["amount"]),
		"currency": str(message["currency"]).upper(),
	}


def handler(event, context):
	results = []
	for record in event.get("Records", []):
		results.append(normalize(record["body"]))
	return results