from __future__ import annotations

from connectivity import describe_connection


def handler(event, context):
	return describe_connection(
		source_subnet_type=event["sourceSubnetType"],
		has_security_group_access=event["securityGroupAccess"],
	)
