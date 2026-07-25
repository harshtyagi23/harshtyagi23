from __future__ import annotations

from producer import make_scheduled_event
from consumer import handler, select_pending


def test_make_scheduled_event_shape() -> None:
	event = make_scheduled_event("2025-02-28T12:00:00Z")

	assert event["source"] == "aws.events"
	assert event["detail-type"] == "Scheduled Event"
	assert event["time"] == "2025-02-28T12:00:00Z"


def test_select_pending_returns_bounded_batch() -> None:
	records = [
		{"id": "1", "status": "PENDING"},
		{"id": "2", "status": "DONE"},
		{"id": "3", "status": "PENDING"},
		{"id": "4", "status": "PENDING"},
	]

	selected = select_pending(records, max_items=2)

	assert [r["id"] for r in selected] == ["1", "3"]


def test_handler_processes_expected_ids(capsys) -> None:
	event = make_scheduled_event("2025-02-28T12:00:00Z")

	result = handler(event, None)

	assert result["count"] == 3
	assert result["processedIds"] == ["task-1", "task-3", "task-4"]
	assert result["triggerTime"] == "2025-02-28T12:00:00Z"
	assert "scheduled run processed" in capsys.readouterr().out