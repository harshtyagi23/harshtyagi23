from __future__ import annotations


def make_read_event(product_id: str) -> dict:
	return {"productId": product_id}


if __name__ == "__main__":
	print(make_read_event("p-1001"))