from router import route


def test_routes_new_paths_to_modern() -> None:
    assert route('/v2/orders') == 'modern'


def test_routes_old_paths_to_legacy() -> None:
    assert route('/v1/orders') == 'legacy'
