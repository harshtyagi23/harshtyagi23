from __future__ import annotations


class _CacheClientPlaceholder:
	def get(self, key: str):
		raise RuntimeError("cache_client must be configured or monkeypatched in tests")

	def set(self, key: str, value: dict, ttl_seconds: int) -> None:
		raise RuntimeError("cache_client must be configured or monkeypatched in tests")


class _DdbClientPlaceholder:
	def get_item(self, **kwargs):
		raise RuntimeError("ddb_client must be configured or monkeypatched in tests")


cache_client = _CacheClientPlaceholder()
ddb_client = _DdbClientPlaceholder()


def _decode_item(item: dict) -> dict:
	return {
		"productId": item["productId"]["S"],
		"name": item["name"]["S"],
	}


def get_product(product_id: str, table_name: str, ttl_seconds: int = 60) -> dict:
	cache_key = f"product:{product_id}"
	cached = cache_client.get(cache_key)
	if cached is not None:
		return {"product": cached, "source": "cache"}

	response = ddb_client.get_item(
		TableName=table_name,
		Key={"productId": {"S": product_id}},
	)

	if "Item" not in response:
		return {"product": None, "source": "database"}

	product = _decode_item(response["Item"])
	cache_client.set(cache_key, product, ttl_seconds)
	return {"product": product, "source": "database"}


def handler(event, context):
	product_id = event["productId"]
	result = get_product(product_id, table_name="ProductsTable", ttl_seconds=60)
	print(f"cache-aside read: productId={product_id}, source={result['source']}")
	return result