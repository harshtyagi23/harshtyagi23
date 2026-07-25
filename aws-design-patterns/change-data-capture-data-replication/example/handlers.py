from __future__ import annotations

from replicator import capture_change, replay_log, replicate_change


def capture_handler(event, context):
	return capture_change(event)


def replicate_handler(event, context):
	return replicate_change(event)


def replay_handler(event, context):
	return {
		"replicated": replay_log(),
	}
