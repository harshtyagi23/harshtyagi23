from __future__ import annotations


def finalize(message: dict) -> dict:
	channel = "manual-review" if message.get("riskTier") == "high" else "auto-approve"
	return {**message, "channel": channel}


def handler(event, context):
	results = []
	for record in event.get("Records", []):
		results.append(finalize(record["body"]))
	return results