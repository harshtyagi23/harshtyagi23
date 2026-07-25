import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as lambdaEventSources from 'aws-cdk-lib/aws-lambda-event-sources';
import * as sqs from 'aws-cdk-lib/aws-sqs';

export class RetryWithBackoffJitterStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const dlq = new sqs.Queue(this, 'RetryDlq', {
      retentionPeriod: cdk.Duration.days(14),
    });

    const queue = new sqs.Queue(this, 'RetryQueue', {
      visibilityTimeout: cdk.Duration.seconds(30),
      deadLetterQueue: {
        queue: dlq,
        maxReceiveCount: 5,
      },
    });

    const worker = new lambda.Function(this, 'RetryWorker', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      timeout: cdk.Duration.seconds(15),
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'retry-worker-ok'}\n",
      ),
      environment: {
        MAX_ATTEMPTS: '5',
        INITIAL_DELAY_MS: '100',
        MAX_DELAY_MS: '2000',
        JITTER_MS: '100',
      },
    });

    worker.addEventSource(
      new lambdaEventSources.SqsEventSource(queue, {
        batchSize: 1,
      }),
    );

    new cdk.CfnOutput(this, 'RetryQueueUrl', { value: queue.queueUrl });
    new cdk.CfnOutput(this, 'RetryDlqUrl', { value: dlq.queueUrl });
  }
}
