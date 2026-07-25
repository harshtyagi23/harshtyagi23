from __future__ import annotations


def can_reach_data_tier(source_subnet_type: str, has_security_group_access: bool) -> bool:
	return source_subnet_type == "private" and has_security_group_access


def describe_connection(source_subnet_type: str, has_security_group_access: bool) -> dict:
	allowed = can_reach_data_tier(source_subnet_type, has_security_group_access)
	return {
		"sourceSubnetType": source_subnet_type,
		"securityGroupAccess": has_security_group_access,
		"allowed": allowed,
		"reason": "private path allowed" if allowed else "public or unauthorized path blocked",
	}
