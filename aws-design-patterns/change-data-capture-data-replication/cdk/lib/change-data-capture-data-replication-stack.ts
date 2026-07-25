import { Stack, StackProps } from 'aws-cdk-lib';
import * as dynamodb from 'aws-cdk-lib/aws-dynamodb';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import { Construct } from 'constructs';

export class ChangeDataCaptureDataReplicationStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const sourceTable = new dynamodb.Table(this, 'SourceTable', {
      partitionKey: { name: 'entityId', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
      stream: dynamodb.StreamViewType.NEW_AND_OLD_IMAGES,
    });

    const targetQueue = new sqs.Queue(this, 'ReplicationQueue');

    const captureFn = new lambda.Function(this, 'CaptureFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.capture_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const replicateFn = new lambda.Function(this, 'ReplicateFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.replicate_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    sourceTable.grantStreamRead(captureFn);
    targetQueue.grantSendMessages(captureFn);
    targetQueue.grantConsumeMessages(replicateFn);
  }
}
