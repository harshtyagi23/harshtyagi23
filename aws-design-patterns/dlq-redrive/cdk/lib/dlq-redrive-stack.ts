import * as cdk from 'aws-cdk-lib';
import { Duration, Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import { SqsEventSource } from 'aws-cdk-lib/aws-lambda-event-sources';
import { Construct } from 'constructs';

export class DlqRedriveStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const deadLetterQueue = new sqs.Queue(this, 'DeadLetterQueue');
    const queue = new sqs.Queue(this, 'PrimaryQueue', {
      visibilityTimeout: Duration.seconds(60),
      deadLetterQueue: {
        queue: deadLetterQueue,
        maxReceiveCount: 3,
      },
    });

    const consumer = new lambda.Function(this, 'DlqConsumer', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer.handler',
      code: lambda.Code.fromAsset('../example'),
      timeout: Duration.seconds(30),
    });

    consumer.addEventSource(new SqsEventSource(queue, { batchSize: 10 }));
  }
}