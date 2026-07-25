from __future__ import annotations

from consumer import get_product, handler
from producer import make_read_event


class FakeCacheClient:
	def __init__(self, initial: dict | None = None) -> None:
		self._store = initial or {}
		self.set_calls = []

	def get(self, key: str):
		return self._store.get(key)

	def set(self, key: str, value: dict, ttl_seconds: int) -> None:
		self._store[key] = value
		self.set_calls.append((key, value, ttl_seconds))


class FakeDdbClient:
	def __init__(self, item: dict | None = None) -> None:
		self.item = item
		self.calls = []

	def get_item(self, **kwargs):
		self.calls.append(kwargs)
		if self.item is None:
			return {}
		return {"Item": self.item}


def test_make_read_event() -> None:
	assert make_read_event("p-1001") == {"productId": "p-1001"}


def test_get_product_cache_hit(monkeypatch) -> None:
	cache = FakeCacheClient(initial={"product:p-1001": {"productId": "p-1001", "name": "Keyboard"}})
	ddb = FakeDdbClient(item=None)
	monkeypatch.setattr("consumer.cache_client", cache)
	monkeypatch.setattr("consumer.ddb_client", ddb)

	result = get_product("p-1001", "ProductsTable", ttl_seconds=120)

	assert result == {
		"product": {"productId": "p-1001", "name": "Keyboard"},
		"source": "cache",
	}
	assert ddb.calls == []


def test_get_product_cache_miss_loads_database(monkeypatch) -> None:
	cache = FakeCacheClient()
	ddb = FakeDdbClient(
		item={
			"productId": {"S": "p-1002"},
			"name": {"S": "Mouse"},
		}
	)
	monkeypatch.setattr("consumer.cache_client", cache)
	monkeypatch.setattr("consumer.ddb_client", ddb)

	result = get_product("p-1002", "ProductsTable", ttl_seconds=90)

	assert result == {
		"product": {"productId": "p-1002", "name": "Mouse"},
		"source": "database",
	}
	assert ddb.calls == [
		{
			"TableName": "ProductsTable",
			"Key": {"productId": {"S": "p-1002"}},
		}
	]
	assert cache.set_calls == [
		("product:p-1002", {"productId": "p-1002", "name": "Mouse"}, 90)
	]


def test_handler_prints_source(capsys, monkeypatch) -> None:
	cache = FakeCacheClient(initial={"product:p-1001": {"productId": "p-1001", "name": "Keyboard"}})
	ddb = FakeDdbClient(item=None)
	monkeypatch.setattr("consumer.cache_client", cache)
	monkeypatch.setattr("consumer.ddb_client", ddb)

	result = handler(make_read_event("p-1001"), None)

	assert result["source"] == "cache"
	assert "cache-aside read: productId=p-1001, source=cache" in capsys.readouterr().out