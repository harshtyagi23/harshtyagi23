import { Duration, Stack, StackProps } from 'aws-cdk-lib';
import * as kinesis from 'aws-cdk-lib/aws-kinesis';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as lambdaEventSources from 'aws-cdk-lib/aws-lambda-event-sources';
import { Construct } from 'constructs';

export class StreamingIngestionStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const stream = new kinesis.Stream(this, 'Clickstream', {
      shardCount: 1,
      streamName: 'clickstream-events',
    });

    const processor = new lambda.Function(this, 'StreamProcessorFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handler.handler',
      code: lambda.Code.fromAsset('../example'),
      timeout: Duration.seconds(30),
    });

    processor.addEventSource(
      new lambdaEventSources.KinesisEventSource(stream, {
        batchSize: 100,
        startingPosition: lambda.StartingPosition.TRIM_HORIZON,
      }),
    );
  }
}
