from __future__ import annotations

from dataclasses import dataclass
from typing import Callable


class SagaStepError(RuntimeError):
    """Raised when a saga step fails."""


@dataclass
class SagaStep:
    name: str
    execute: Callable[[dict], dict]
    compensate: Callable[[dict], dict]


def run_saga(steps: list[SagaStep], context: dict) -> dict:
    completed: list[SagaStep] = []
    events: list[dict] = []

    for step in steps:
        try:
            result = step.execute(context)
            completed.append(step)
            events.append({"step": step.name, "phase": "execute", "result": result})
        except Exception as exc:  # noqa: BLE001
            compensation_events: list[dict] = []
            for done in reversed(completed):
                comp = done.compensate(context)
                compensation_events.append(
                    {
                        "step": done.name,
                        "phase": "compensate",
                        "result": comp,
                    }
                )
            return {
                "status": "compensated",
                "failedStep": step.name,
                "reason": str(exc),
                "events": events,
                "compensations": compensation_events,
            }

    return {
        "status": "ok",
        "events": events,
        "compensations": [],
    }
