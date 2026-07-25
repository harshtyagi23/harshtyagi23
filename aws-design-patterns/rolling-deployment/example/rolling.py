from __future__ import annotations


def rollout_batches(total: int, batch_size: int) -> list[tuple[int, int]]:
    batches = []
    replaced = 0
    while replaced < total:
        start = replaced + 1
        replaced = min(total, replaced + batch_size)
        batches.append((start, replaced))
    return batches


def should_pause(error_rate: float, threshold: float = 0.02) -> bool:
    return error_rate > threshold
