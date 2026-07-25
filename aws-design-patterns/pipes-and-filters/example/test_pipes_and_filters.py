from __future__ import annotations

from producer import make_input
from filter1 import normalize
from filter2 import enrich
from filter3 import finalize


def test_normalize_step() -> None:
	message = make_input("order-123", 149.99, "usd")

	normalized = normalize(message)

	assert normalized == {
		"orderId": "order-123",
		"amount": 149.99,
		"currency": "USD",
	}


def test_enrich_step_standard_risk() -> None:
	message = {"orderId": "order-1", "amount": 200.0, "currency": "USD"}

	enriched = enrich(message)

	assert enriched["riskTier"] == "standard"


def test_enrich_step_high_risk() -> None:
	message = {"orderId": "order-2", "amount": 1200.0, "currency": "USD"}

	enriched = enrich(message)

	assert enriched["riskTier"] == "high"


def test_finalize_routes_high_risk_to_manual_review() -> None:
	message = {
		"orderId": "order-2",
		"amount": 1200.0,
		"currency": "USD",
		"riskTier": "high",
	}

	final = finalize(message)

	assert final["channel"] == "manual-review"


def test_finalize_routes_standard_to_auto() -> None:
	message = {
		"orderId": "order-3",
		"amount": 200.0,
		"currency": "USD",
		"riskTier": "standard",
	}

	final = finalize(message)

	assert final["channel"] == "auto-approve"