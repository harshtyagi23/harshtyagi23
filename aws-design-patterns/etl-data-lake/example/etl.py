from __future__ import annotations


def transform_record(record: dict) -> dict:
	return {
		"order_id": record["order_id"],
		"amount": float(record["amount"]),
		"country": record["country"].upper(),
		"amount_bucket": "large" if float(record["amount"]) >= 1000 else "standard",
	}


def run_etl(raw_records: list[dict]) -> dict:
	curated = [transform_record(record) for record in raw_records]
	return {
		"rawCount": len(raw_records),
		"curatedCount": len(curated),
		"records": curated,
	}
