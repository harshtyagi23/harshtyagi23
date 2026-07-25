from __future__ import annotations

from dataclasses import dataclass


class CircuitOpenError(RuntimeError):
    """Raised when the circuit is open and the call is rejected."""


@dataclass
class CircuitBreaker:
    failure_threshold: int = 3
    recovery_timeout_seconds: int = 30

    def __post_init__(self) -> None:
        self.state = "CLOSED"
        self.failure_count = 0
        self.opened_at = 0

    def allow_request(self, now: int) -> bool:
        if self.state == "OPEN":
            if now - self.opened_at >= self.recovery_timeout_seconds:
                self.state = "HALF_OPEN"
                return True
            return False
        return True

    def on_success(self) -> None:
        self.state = "CLOSED"
        self.failure_count = 0

    def on_failure(self, now: int) -> None:
        self.failure_count += 1
        if self.state == "HALF_OPEN" or self.failure_count >= self.failure_threshold:
            self.state = "OPEN"
            self.opened_at = now
