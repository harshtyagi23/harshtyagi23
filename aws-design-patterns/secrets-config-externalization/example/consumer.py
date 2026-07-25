from __future__ import annotations

import json


class _SecretsClientPlaceholder:
	def get_secret_value(self, **kwargs):
		raise RuntimeError("secrets_client must be configured or monkeypatched in tests")


class _SsmClientPlaceholder:
	def get_parameter(self, **kwargs):
		raise RuntimeError("ssm_client must be configured or monkeypatched in tests")


secrets_client = _SecretsClientPlaceholder()
ssm_client = _SsmClientPlaceholder()


def get_config(parameter_name: str) -> str:
	response = ssm_client.get_parameter(Name=parameter_name)
	return response["Parameter"]["Value"]


def get_secret(secret_id: str) -> dict:
	response = secrets_client.get_secret_value(SecretId=secret_id)
	return json.loads(response["SecretString"])


def handler(event, context):
	parameter_name = event["parameterName"]
	secret_id = event["secretId"]

	base_url = get_config(parameter_name)
	credentials = get_secret(secret_id)

	username = credentials.get("username", "unknown")
	print(f"loaded config for {base_url} as user {username}")
	return {
		"baseUrl": base_url,
		"username": username,
	}