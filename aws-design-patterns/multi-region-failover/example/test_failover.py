from failover import select_region


def test_selects_primary_when_healthy() -> None:
    assert select_region(True) == 'primary'


def test_fails_over_to_secondary() -> None:
    assert select_region(False) == 'secondary'
