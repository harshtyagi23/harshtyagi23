from __future__ import annotations

from aggregator import gather, handle_request, scatter
from producer import make_search_request


def test_scatter_calls_all_services() -> None:
	request = make_search_request("laptop")
	responses = scatter(request)

	assert len(responses) == 3
	assert {response["source"] for response in responses} == {
		"catalog",
		"reviews",
		"pricing",
	}


def test_gather_merges_results() -> None:
	request = make_search_request("camera")
	result = handle_request(request)

	assert result["resultCount"] == 4
	assert "catalog:camera:a" in result["results"]
	assert "pricing:camera:best-offer" in result["results"]


def test_gather_preserves_sources() -> None:
	responses = [
		{"source": "catalog", "results": ["a"]},
		{"source": "reviews", "results": ["b"]},
	]
	result = gather(responses)

	assert result["sources"] == ["catalog", "reviews"]
