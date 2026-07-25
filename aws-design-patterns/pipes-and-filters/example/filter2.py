from __future__ import annotations


def enrich(message: dict) -> dict:
	risk_tier = "high" if message["amount"] >= 1000 else "standard"
	return {**message, "riskTier": risk_tier}


def handler(event, context):
	results = []
	for record in event.get("Records", []):
		results.append(enrich(record["body"]))
	return results