import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as s3 from 'aws-cdk-lib/aws-s3';
import { Construct } from 'constructs';

export class EtlDataLakeStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const rawBucket = new s3.Bucket(this, 'RawDataBucket');
    const curatedBucket = new s3.Bucket(this, 'CuratedDataBucket');

    const etlFn = new lambda.Function(this, 'EtlProcessorFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handler.handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        RAW_BUCKET_NAME: rawBucket.bucketName,
        CURATED_BUCKET_NAME: curatedBucket.bucketName,
      },
    });

    rawBucket.grantRead(etlFn);
    curatedBucket.grantWrite(etlFn);
  }
}
