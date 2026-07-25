from __future__ import annotations


def make_access_request(source_subnet_type: str, has_security_group_access: bool) -> dict:
	return {
		"sourceSubnetType": source_subnet_type,
		"securityGroupAccess": has_security_group_access,
	}
