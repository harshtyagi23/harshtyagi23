from __future__ import annotations


def make_scheduled_event(trigger_time: str) -> dict:
	return {
		"id": "scheduled-evt-1",
		"source": "aws.events",
		"detail-type": "Scheduled Event",
		"time": trigger_time,
	}


if __name__ == "__main__":
	print(make_scheduled_event("2025-02-28T12:00:00Z"))