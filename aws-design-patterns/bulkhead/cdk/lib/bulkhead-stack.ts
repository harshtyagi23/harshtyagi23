import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as lambdaEventSources from 'aws-cdk-lib/aws-lambda-event-sources';
import * as sqs from 'aws-cdk-lib/aws-sqs';

export class BulkheadStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const premiumQueue = new sqs.Queue(this, 'PremiumQueue', {
      visibilityTimeout: cdk.Duration.seconds(30),
    });

    const standardQueue = new sqs.Queue(this, 'StandardQueue', {
      visibilityTimeout: cdk.Duration.seconds(30),
    });

    const premiumWorker = new lambda.Function(this, 'PremiumWorker', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      reservedConcurrentExecutions: 50,
      timeout: cdk.Duration.seconds(15),
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'premium-ok'}\n",
      ),
      environment: {
        SEGMENT: 'premium',
      },
    });

    const standardWorker = new lambda.Function(this, 'StandardWorker', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      reservedConcurrentExecutions: 10,
      timeout: cdk.Duration.seconds(15),
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'standard-ok'}\n",
      ),
      environment: {
        SEGMENT: 'standard',
      },
    });

    premiumWorker.addEventSource(new lambdaEventSources.SqsEventSource(premiumQueue, { batchSize: 1 }));
    standardWorker.addEventSource(new lambdaEventSources.SqsEventSource(standardQueue, { batchSize: 1 }));

    new cdk.CfnOutput(this, 'PremiumQueueUrl', { value: premiumQueue.queueUrl });
    new cdk.CfnOutput(this, 'StandardQueueUrl', { value: standardQueue.queueUrl });
  }
}
