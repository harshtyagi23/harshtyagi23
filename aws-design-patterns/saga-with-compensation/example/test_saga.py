from __future__ import annotations

from saga import SagaStep, SagaStepError, run_saga


def test_saga_success() -> None:
    context = {"orderId": "ord-1"}

    steps = [
        SagaStep(
            name="ReserveInventory",
            execute=lambda ctx: {"reservationId": f"res-{ctx['orderId']}"},
            compensate=lambda _ctx: {"released": True},
        ),
        SagaStep(
            name="CapturePayment",
            execute=lambda ctx: {"paymentId": f"pay-{ctx['orderId']}"},
            compensate=lambda _ctx: {"refunded": True},
        ),
    ]

    result = run_saga(steps, context)

    assert result["status"] == "ok"
    assert len(result["events"]) == 2
    assert result["compensations"] == []


def test_saga_failure_runs_reverse_compensation() -> None:
    context = {"orderId": "ord-2"}

    def fail_step(_: dict) -> dict:
        raise SagaStepError("shipment-failed")

    steps = [
        SagaStep(
            name="ReserveInventory",
            execute=lambda _ctx: {"reservationId": "res-1"},
            compensate=lambda _ctx: {"released": True},
        ),
        SagaStep(
            name="CapturePayment",
            execute=lambda _ctx: {"paymentId": "pay-1"},
            compensate=lambda _ctx: {"refunded": True},
        ),
        SagaStep(
            name="CreateShipment",
            execute=fail_step,
            compensate=lambda _ctx: {"cancelled": True},
        ),
    ]

    result = run_saga(steps, context)

    assert result["status"] == "compensated"
    assert result["failedStep"] == "CreateShipment"
    assert [c["step"] for c in result["compensations"]] == [
        "CapturePayment",
        "ReserveInventory",
    ]


def test_failure_on_first_step_has_no_compensation() -> None:
    def fail_first(_: dict) -> dict:
        raise SagaStepError("inventory-unavailable")

    steps = [
        SagaStep(
            name="ReserveInventory",
            execute=fail_first,
            compensate=lambda _ctx: {"released": True},
        )
    ]

    result = run_saga(steps, {"orderId": "ord-3"})

    assert result["status"] == "compensated"
    assert result["failedStep"] == "ReserveInventory"
    assert result["compensations"] == []
