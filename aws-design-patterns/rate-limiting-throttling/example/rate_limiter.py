"""Token bucket rate limiter implementation."""

import time
from typing import Optional, Dict, Tuple


class TokenBucket:
    """Token bucket rate limiter allowing bursts within capacity."""

    def __init__(self, capacity: int, refill_rate: float):
        """
        Initialize token bucket.

        Args:
            capacity: Maximum number of tokens (burst capacity)
            refill_rate: Tokens added per second
        """
        self.capacity = capacity
        self.refill_rate = refill_rate
        self.tokens = float(capacity)
        self.last_refill = time.time()

    def allow_request(self, tokens_needed: int = 1) -> Tuple[bool, int]:
        """
        Check if request is allowed and consume tokens.

        Args:
            tokens_needed: Number of tokens this request requires

        Returns:
            Tuple of (allowed: bool, remaining_tokens: int)
        """
        self._refill()

        if self.tokens >= tokens_needed:
            self.tokens -= tokens_needed
            return True, int(self.tokens)
        return False, int(self.tokens)

    def _refill(self) -> None:
        """Refill tokens based on time elapsed."""
        now = time.time()
        elapsed = now - self.last_refill
        tokens_to_add = elapsed * self.refill_rate
        self.tokens = min(self.capacity, self.tokens + tokens_to_add)
        self.last_refill = now

    def get_retry_after(self) -> float:
        """Calculate seconds until next token available."""
        self._refill()
        if self.tokens < 1:
            tokens_needed = 1 - self.tokens
            return tokens_needed / self.refill_rate
        return 0


class RateLimiter:
    """Per-client rate limiter using token buckets."""

    def __init__(self, capacity: int = 100, refill_rate: float = 10.0):
        """
        Initialize rate limiter for multiple clients.

        Args:
            capacity: Burst capacity per client
            refill_rate: Tokens per second per client
        """
        self.capacity = capacity
        self.refill_rate = refill_rate
        self.buckets: Dict[str, TokenBucket] = {}

    def allow_request(self, client_id: str, tokens_needed: int = 1) -> Tuple[bool, Optional[float]]:
        """
        Check if client's request is allowed.

        Args:
            client_id: Unique client identifier (IP, user ID, API key)
            tokens_needed: Tokens required for this request

        Returns:
            Tuple of (allowed: bool, retry_after_seconds: Optional[float])
        """
        if client_id not in self.buckets:
            self.buckets[client_id] = TokenBucket(self.capacity, self.refill_rate)

        bucket = self.buckets[client_id]
        allowed, _ = bucket.allow_request(tokens_needed)

        if allowed:
            return True, None

        retry_after = bucket.get_retry_after()
        return False, retry_after

    def get_stats(self, client_id: str) -> Optional[Dict]:
        """Get current bucket stats for client."""
        if client_id not in self.buckets:
            return None
        bucket = self.buckets[client_id]
        return {
            "capacity": bucket.capacity,
            "tokens_available": int(bucket.tokens),
            "refill_rate": bucket.refill_rate,
        }
