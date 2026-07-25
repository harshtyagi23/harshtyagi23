from __future__ import annotations

from etl import run_etl, transform_record
from lambda_handler import handler
from producer import make_raw_record


def test_transform_record_normalizes_shape() -> None:
	record = make_raw_record("o-1", 1250, "us")
	transformed = transform_record(record)

	assert transformed["country"] == "US"
	assert transformed["amount_bucket"] == "large"


def test_run_etl_returns_curated_batch() -> None:
	records = [
		make_raw_record("o-1", 50, "us"),
		make_raw_record("o-2", 1200, "in"),
	]
	result = run_etl(records)

	assert result["rawCount"] == 2
	assert result["curatedCount"] == 2
	assert result["records"][1]["amount_bucket"] == "large"


def test_lambda_handler_processes_event_records() -> None:
	event = {
		"records": [
			make_raw_record("o-3", 99, "de"),
		],
	}
	result = handler(event, None)

	assert result["curatedCount"] == 1
	assert result["records"][0]["country"] == "DE"
