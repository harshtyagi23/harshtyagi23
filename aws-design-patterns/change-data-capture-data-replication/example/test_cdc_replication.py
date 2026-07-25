from __future__ import annotations

from handlers import capture_handler, replay_handler, replicate_handler
from producer import make_change_event
from replicator import SOURCE_LOG, TARGET_STORE


def setup_function() -> None:
	SOURCE_LOG.clear()
	TARGET_STORE.clear()


def test_capture_adds_event_to_source_log() -> None:
	event = make_change_event("cust-1", "UPSERT", {"email": "a@example.com"})
	capture_handler(event, None)

	assert len(SOURCE_LOG) == 1
	assert SOURCE_LOG[0]["entityId"] == "cust-1"


def test_replicate_updates_target_store() -> None:
	event = make_change_event("cust-2", "UPSERT", {"email": "b@example.com"})
	result = replicate_handler(event, None)

	assert result["entityId"] == "cust-2"
	assert TARGET_STORE["cust-2"]["payload"]["email"] == "b@example.com"


def test_replay_replicates_all_captured_events() -> None:
	capture_handler(make_change_event("cust-3", "UPSERT", {"tier": "gold"}), None)
	capture_handler(make_change_event("cust-4", "DELETE", {"active": False}), None)
	result = replay_handler({}, None)

	assert len(result["replicated"]) == 2
	assert TARGET_STORE["cust-4"]["replicatedOperation"] == "DELETE"
