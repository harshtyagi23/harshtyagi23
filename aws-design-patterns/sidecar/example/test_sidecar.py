from __future__ import annotations

from composition import invoke_with_sidecar
from sidecar import auth_proxy, enrich_log


def test_auth_proxy_blocks_missing_token() -> None:
	result = auth_proxy({})
	assert result["authorized"] is False
	assert result["reason"] == "missing token"


def test_invoke_with_sidecar_enriches_logs() -> None:
	result = invoke_with_sidecar("/health", {"authorization": "Bearer abc"})
	assert result["response"]["status"] == 200
	assert result["sidecarLog"]["service"] == "log-sidecar"
	assert result["sidecarLog"]["path"] == "/health"


def test_enrich_log_preserves_status() -> None:
	record = enrich_log({"path": "/ready", "status": 200})
	assert record["status"] == 200
	assert "forwarded log" in record["message"]
