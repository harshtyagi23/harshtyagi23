from __future__ import annotations

SOURCE_LOG: list[dict] = []
TARGET_STORE: dict[str, dict] = {}


def capture_change(event: dict) -> dict:
	SOURCE_LOG.append(event)
	return event


def replicate_change(event: dict) -> dict:
	TARGET_STORE[event["entityId"]] = {
		"entityId": event["entityId"],
		"replicatedOperation": event["operation"],
		"payload": event["payload"],
	}
	return TARGET_STORE[event["entityId"]]


def replay_log() -> list[dict]:
	return [replicate_change(event) for event in SOURCE_LOG]
