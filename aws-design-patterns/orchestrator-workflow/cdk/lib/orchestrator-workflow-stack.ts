import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sfn from 'aws-cdk-lib/aws-stepfunctions';
import * as tasks from 'aws-cdk-lib/aws-stepfunctions-tasks';
import { Construct } from 'constructs';

export class OrchestratorWorkflowStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const validateFn = new lambda.Function(this, 'ValidateOrderFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.validate_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const reserveFn = new lambda.Function(this, 'ReserveInventoryFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.reserve_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const chargeFn = new lambda.Function(this, 'ChargePaymentFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.charge_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const shipFn = new lambda.Function(this, 'ShipOrderFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.ship_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const validateTask = new tasks.LambdaInvoke(this, 'ValidateOrder', {
      lambdaFunction: validateFn,
      outputPath: '$.Payload',
    });

    const reserveTask = new tasks.LambdaInvoke(this, 'ReserveInventory', {
      lambdaFunction: reserveFn,
      outputPath: '$.Payload',
    });

    const chargeTask = new tasks.LambdaInvoke(this, 'ChargePayment', {
      lambdaFunction: chargeFn,
      outputPath: '$.Payload',
    });

    const shipTask = new tasks.LambdaInvoke(this, 'ShipOrder', {
      lambdaFunction: shipFn,
      outputPath: '$.Payload',
    });

    const failValidate = new sfn.Fail(this, 'ValidationFailed', {
      cause: 'Validation failed',
      error: 'ValidationError',
    });

    const failReserve = new sfn.Fail(this, 'InventoryReservationFailed', {
      cause: 'Inventory reservation failed',
      error: 'InventoryError',
    });

    const failPayment = new sfn.Fail(this, 'PaymentFailed', {
      cause: 'Payment authorization failed',
      error: 'PaymentError',
    });

    const success = new sfn.Succeed(this, 'OrderWorkflowSucceeded');

    const paymentChoice = new sfn.Choice(this, 'PaymentCharged?')
      .when(sfn.Condition.stringEquals('$.status', 'OK'), shipTask.next(success))
      .otherwise(failPayment);

    const reserveChoice = new sfn.Choice(this, 'InventoryReserved?')
      .when(sfn.Condition.stringEquals('$.status', 'OK'), chargeTask.next(paymentChoice))
      .otherwise(failReserve);

    const validateChoice = new sfn.Choice(this, 'ValidationSucceeded?')
      .when(sfn.Condition.stringEquals('$.status', 'OK'), reserveTask.next(reserveChoice))
      .otherwise(failValidate);

    const definition = validateTask.next(validateChoice);

    new sfn.StateMachine(this, 'OrderOrchestratorStateMachine', {
      definitionBody: sfn.DefinitionBody.fromChainable(definition),
      stateMachineName: 'order-orchestrator-workflow',
    });
  }
}
