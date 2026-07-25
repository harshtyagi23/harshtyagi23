from __future__ import annotations

from consumer import get_config, get_secret, handler
from producer import make_bootstrap_event


class FakeSecretsClient:
	def __init__(self) -> None:
		self.calls = []

	def get_secret_value(self, **kwargs):
		self.calls.append(kwargs)
		return {"SecretString": '{"username":"app_user","password":"p@ss"}'}


class FakeSsmClient:
	def __init__(self) -> None:
		self.calls = []

	def get_parameter(self, **kwargs):
		self.calls.append(kwargs)
		return {"Parameter": {"Value": "https://api.internal.example"}}


def test_make_bootstrap_event() -> None:
	event = make_bootstrap_event("db/credentials", "/sample/api/base-url")

	assert event == {
		"secretId": "db/credentials",
		"parameterName": "/sample/api/base-url",
	}


def test_get_config(monkeypatch) -> None:
	fake = FakeSsmClient()
	monkeypatch.setattr("consumer.ssm_client", fake)

	value = get_config("/sample/api/base-url")

	assert value == "https://api.internal.example"
	assert fake.calls == [{"Name": "/sample/api/base-url"}]


def test_get_secret(monkeypatch) -> None:
	fake = FakeSecretsClient()
	monkeypatch.setattr("consumer.secrets_client", fake)

	secret = get_secret("db/credentials")

	assert secret == {"username": "app_user", "password": "p@ss"}
	assert fake.calls == [{"SecretId": "db/credentials"}]


def test_handler_loads_secret_and_config(capsys, monkeypatch) -> None:
	fake_ssm = FakeSsmClient()
	fake_sm = FakeSecretsClient()
	monkeypatch.setattr("consumer.ssm_client", fake_ssm)
	monkeypatch.setattr("consumer.secrets_client", fake_sm)

	event = make_bootstrap_event("db/credentials", "/sample/api/base-url")
	result = handler(event, None)

	assert result == {
		"baseUrl": "https://api.internal.example",
		"username": "app_user",
	}
	assert "loaded config for https://api.internal.example as user app_user" in capsys.readouterr().out