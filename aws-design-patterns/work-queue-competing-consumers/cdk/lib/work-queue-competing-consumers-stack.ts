import * as cdk from 'aws-cdk-lib';
import { Duration, Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import { SqsEventSource } from 'aws-cdk-lib/aws-lambda-event-sources';
import { Construct } from 'constructs';

export class WorkQueueCompetingConsumersStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const queue = new sqs.Queue(this, 'WorkQueue', {
      visibilityTimeout: Duration.seconds(60),
    });

    const workerA = new lambda.Function(this, 'WorkerA', {
      runtime: lambda.Runtime.PYTHON_3_12,
      code: lambda.Code.fromAsset('../example'),
      handler: 'worker.handler',
      timeout: Duration.seconds(30),
    });

    const workerB = new lambda.Function(this, 'WorkerB', {
      runtime: lambda.Runtime.PYTHON_3_12,
      code: lambda.Code.fromAsset('../example'),
      handler: 'worker.handler',
      timeout: Duration.seconds(30),
    });

    workerA.addEventSource(new SqsEventSource(queue));
    workerB.addEventSource(new SqsEventSource(queue));
  }
}