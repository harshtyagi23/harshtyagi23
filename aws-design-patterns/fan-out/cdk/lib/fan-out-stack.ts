import { Stack, StackProps, Duration } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as sns from 'aws-cdk-lib/aws-sns';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import * as subs from 'aws-cdk-lib/aws-sns-subscriptions';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as lambdaEventSources from 'aws-cdk-lib/aws-lambda-event-sources';

export class FanOutStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const topic = new sns.Topic(this, 'FanOutTopic');
    const queueA = new sqs.Queue(this, 'QueueA', {
      visibilityTimeout: Duration.seconds(30),
    });
    const queueB = new sqs.Queue(this, 'QueueB', {
      visibilityTimeout: Duration.seconds(30),
    });

    topic.addSubscription(new subs.SqsSubscription(queueA));
    topic.addSubscription(new subs.SqsSubscription(queueB));

    const consumerA = new lambda.Function(this, 'ConsumerA', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer_a.handler',
      code: lambda.Code.fromAsset('../example'),
      timeout: Duration.seconds(10),
    });

    const consumerB = new lambda.Function(this, 'ConsumerB', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer_b.handler',
      code: lambda.Code.fromAsset('../example'),
      timeout: Duration.seconds(10),
    });

    consumerA.addEventSource(new lambdaEventSources.SqsEventSource(queueA));
    consumerB.addEventSource(new lambdaEventSources.SqsEventSource(queueB));
  }
}
