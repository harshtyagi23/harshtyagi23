import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sfn from 'aws-cdk-lib/aws-stepfunctions';
import * as tasks from 'aws-cdk-lib/aws-stepfunctions-tasks';

export class SagaWithCompensationStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const reserveFn = this.makeStepFunction('ReserveInventoryHandler', 'reserve-ok');
    const payFn = this.makeStepFunction('CapturePaymentHandler', 'payment-ok');
    const shipFn = this.makeStepFunction('CreateShipmentHandler', 'shipment-ok');
    const refundFn = this.makeStepFunction('RefundPaymentHandler', 'refund-ok');
    const releaseFn = this.makeStepFunction('ReleaseInventoryHandler', 'release-ok');

    const reserveTask = new tasks.LambdaInvoke(this, 'ReserveInventory', {
      lambdaFunction: reserveFn,
      outputPath: '$.Payload',
    });

    const payTask = new tasks.LambdaInvoke(this, 'CapturePayment', {
      lambdaFunction: payFn,
      outputPath: '$.Payload',
    });

    const shipTask = new tasks.LambdaInvoke(this, 'CreateShipment', {
      lambdaFunction: shipFn,
      outputPath: '$.Payload',
    });

    const refundTask = new tasks.LambdaInvoke(this, 'RefundPayment', {
      lambdaFunction: refundFn,
      outputPath: '$.Payload',
    });

    const releaseTask = new tasks.LambdaInvoke(this, 'ReleaseInventory', {
      lambdaFunction: releaseFn,
      outputPath: '$.Payload',
    });

    const success = new sfn.Succeed(this, 'SagaCompleted');
    const compensated = new sfn.Succeed(this, 'SagaCompensated');

    const compensationChain = refundTask.next(releaseTask).next(compensated);

    shipTask.addCatch(compensationChain, {
      errors: ['States.ALL'],
      resultPath: '$.error',
    });

    const workflow = reserveTask
      .next(payTask)
      .next(shipTask)
      .next(success);

    const stateMachine = new sfn.StateMachine(this, 'SagaStateMachine', {
      definitionBody: sfn.DefinitionBody.fromChainable(workflow),
      timeout: cdk.Duration.minutes(5),
    });

    new cdk.CfnOutput(this, 'StateMachineArn', {
      value: stateMachine.stateMachineArn,
    });
  }

  private makeStepFunction(id: string, message: string): lambda.Function {
    return new lambda.Function(this, id, {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      timeout: cdk.Duration.seconds(10),
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          `    return {'status': '${message}', 'input': event}\n`,
      ),
    });
  }
}
