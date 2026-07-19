import * as cdk from 'aws-cdk-lib';
import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as s3 from 'aws-cdk-lib/aws-s3';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import { SqsEventSource } from 'aws-cdk-lib/aws-lambda-event-sources';
import { Construct } from 'constructs';

export class ClaimCheckStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const bucket = new s3.Bucket(this, 'ClaimCheckBucket');
    const queue = new sqs.Queue(this, 'ClaimCheckQueue');

    const consumer = new lambda.Function(this, 'ClaimCheckConsumer', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer.handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        BUCKET_NAME: bucket.bucketName,
      },
    });

    queue.grantConsumeMessages(consumer);
    bucket.grantRead(consumer);
    consumer.addEventSource(new SqsEventSource(queue));
  }
}