from __future__ import annotations

from dataclasses import dataclass, field


@dataclass
class BulkheadLimiter:
    limits: dict[str, int]
    in_flight: dict[str, int] = field(default_factory=dict)

    def __post_init__(self) -> None:
        for pool in self.limits:
            self.in_flight.setdefault(pool, 0)

    def try_acquire(self, pool_name: str) -> bool:
        if pool_name not in self.limits:
            raise KeyError(f"unknown pool: {pool_name}")
        if self.in_flight[pool_name] >= self.limits[pool_name]:
            return False
        self.in_flight[pool_name] += 1
        return True

    def release(self, pool_name: str) -> None:
        if pool_name not in self.limits:
            raise KeyError(f"unknown pool: {pool_name}")
        if self.in_flight[pool_name] <= 0:
            return
        self.in_flight[pool_name] -= 1


def classify(request: dict[str, str]) -> str:
    tier = request.get("tenantTier", "standard").lower()
    return "premium" if tier == "premium" else "standard"


def pool_for_segment(segment: str) -> str:
    return f"{segment}-workers"


def handle_request(limiter: BulkheadLimiter, request: dict[str, str]) -> dict[str, str | int]:
    segment = classify(request)
    pool = pool_for_segment(segment)

    if not limiter.try_acquire(pool):
        return {
            "status": "rejected",
            "segment": segment,
            "reason": "pool-saturated",
        }

    try:
        return {
            "status": "accepted",
            "segment": segment,
            "pool": pool,
            "concurrencyLimit": limiter.limits[pool],
        }
    finally:
        limiter.release(pool)
