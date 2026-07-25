from rolling import rollout_batches, should_pause


def test_batches_cover_all_instances() -> None:
    assert rollout_batches(10, 3) == [(1, 3), (4, 6), (7, 9), (10, 10)]


def test_pause_on_high_error_rate() -> None:
    assert should_pause(0.03) is True
    assert should_pause(0.01) is False
