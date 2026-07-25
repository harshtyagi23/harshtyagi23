from __future__ import annotations

from bulkhead import BulkheadLimiter, handle_request


def build_limiter() -> BulkheadLimiter:
    return BulkheadLimiter(
        limits={
            "premium-workers": 2,
            "standard-workers": 1,
        }
    )


def test_accepts_request_when_capacity_available() -> None:
    limiter = build_limiter()

    response = handle_request(limiter, {"tenantTier": "premium"})

    assert response["status"] == "accepted"
    assert response["segment"] == "premium"


def test_rejects_when_standard_pool_saturated() -> None:
    limiter = build_limiter()

    assert limiter.try_acquire("standard-workers") is True
    response = handle_request(limiter, {"tenantTier": "standard"})

    assert response["status"] == "rejected"
    assert response["reason"] == "pool-saturated"


def test_premium_still_works_when_standard_is_saturated() -> None:
    limiter = build_limiter()

    assert limiter.try_acquire("standard-workers") is True
    response = handle_request(limiter, {"tenantTier": "premium"})

    assert response["status"] == "accepted"
    assert response["pool"] == "premium-workers"


def test_release_restores_capacity() -> None:
    limiter = build_limiter()

    assert limiter.try_acquire("standard-workers") is True
    limiter.release("standard-workers")

    response = handle_request(limiter, {"tenantTier": "standard"})
    assert response["status"] == "accepted"
