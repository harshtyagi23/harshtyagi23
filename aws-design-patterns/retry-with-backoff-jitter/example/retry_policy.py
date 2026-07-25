from __future__ import annotations

import random
from dataclasses import dataclass
from typing import Any, Callable


class RetryableError(RuntimeError):
    """Represents transient failure that can be retried."""


class NonRetryableError(RuntimeError):
    """Represents failure that should not be retried."""


@dataclass(frozen=True)
class RetryConfig:
    max_attempts: int = 5
    initial_delay_ms: int = 100
    max_delay_ms: int = 2_000
    jitter_ms: int = 100
    seed: int = 7


class RetryExecutor:
    def __init__(self, config: RetryConfig) -> None:
        self.config = config
        self.rng = random.Random(config.seed)

    def next_delay_ms(self, attempt: int) -> tuple[int, int, int]:
        base_delay = min(
            self.config.max_delay_ms,
            self.config.initial_delay_ms * (2 ** (attempt - 1)),
        )
        jitter = self.rng.randint(0, self.config.jitter_ms)
        return base_delay + jitter, base_delay, jitter

    def run(self, operation: Callable[[], dict[str, Any]]) -> dict[str, Any]:
        events: list[dict[str, int | str]] = []
        for attempt in range(1, self.config.max_attempts + 1):
            try:
                result = operation()
                return {
                    "status": "ok",
                    "attempts": attempt,
                    "result": result,
                    "events": events,
                }
            except NonRetryableError as exc:
                return {
                    "status": "failed",
                    "attempts": attempt,
                    "error": str(exc),
                    "events": events,
                }
            except RetryableError as exc:
                if attempt == self.config.max_attempts:
                    return {
                        "status": "failed",
                        "attempts": attempt,
                        "error": str(exc),
                        "events": events,
                    }
                sleep_ms, base_delay, jitter = self.next_delay_ms(attempt)
                events.append(
                    {
                        "attempt": attempt,
                        "reason": str(exc),
                        "sleepMs": sleep_ms,
                        "baseDelayMs": base_delay,
                        "jitterMs": jitter,
                    }
                )

        return {
            "status": "failed",
            "attempts": self.config.max_attempts,
            "error": "exhausted",
            "events": events,
        }
