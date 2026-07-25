def select_region(primary_healthy: bool) -> str:
    return 'primary' if primary_healthy else 'secondary'
