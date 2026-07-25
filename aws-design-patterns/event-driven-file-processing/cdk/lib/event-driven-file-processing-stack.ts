import * as s3 from 'aws-cdk-lib/aws-s3';
import * as s3n from 'aws-cdk-lib/aws-s3-notifications';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import { Stack, StackProps } from 'aws-cdk-lib';
import { Construct } from 'constructs';

export class EventDrivenFileProcessingStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const bucket = new s3.Bucket(this, 'UploadsBucket');

    const processor = new lambda.Function(this, 'FileProcessor', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer.handler',
      code: lambda.Code.fromAsset('../example'),
    });

    bucket.grantRead(processor);
    bucket.addEventNotification(s3.EventType.OBJECT_CREATED, new s3n.LambdaDestination(processor));
  }
}