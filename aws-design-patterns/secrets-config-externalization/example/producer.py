from __future__ import annotations


def make_bootstrap_event(secret_id: str, parameter_name: str) -> dict:
	return {
		"secretId": secret_id,
		"parameterName": parameter_name,
	}


if __name__ == "__main__":
	print(make_bootstrap_event("db/credentials", "/sample/api/base-url"))