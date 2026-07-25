def route(path: str) -> str:
    if path.startswith('/v2/'):
        return 'modern'
    return 'legacy'
