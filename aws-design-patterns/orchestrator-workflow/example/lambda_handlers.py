from __future__ import annotations

from orchestrator import run_workflow
from workflow import charge_payment, reserve_inventory, ship_order, validate_order


def validate_handler(event, context):
	return validate_order(event)


def reserve_handler(event, context):
	return reserve_inventory(event)


def charge_handler(event, context):
	return charge_payment(event)


def ship_handler(event, context):
	return ship_order(event)


def orchestrator_handler(event, context):
	return run_workflow(event)
