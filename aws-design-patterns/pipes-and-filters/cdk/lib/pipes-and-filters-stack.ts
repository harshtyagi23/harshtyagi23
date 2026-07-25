import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import * as sources from 'aws-cdk-lib/aws-lambda-event-sources';
import { Construct } from 'constructs';

export class PipesAndFiltersStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const pipeQueue1 = new sqs.Queue(this, 'PipeQueue1');
    const pipeQueue2 = new sqs.Queue(this, 'PipeQueue2');
    const pipeQueue3 = new sqs.Queue(this, 'PipeQueue3');

    const filter1 = new lambda.Function(this, 'Filter1Function', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'filter1.handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const filter2 = new lambda.Function(this, 'Filter2Function', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'filter2.handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const filter3 = new lambda.Function(this, 'Filter3Function', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'filter3.handler',
      code: lambda.Code.fromAsset('../example'),
    });

    filter1.addEventSource(new sources.SqsEventSource(pipeQueue1));
    filter2.addEventSource(new sources.SqsEventSource(pipeQueue2));
    filter3.addEventSource(new sources.SqsEventSource(pipeQueue3));

    pipeQueue1.grantConsumeMessages(filter1);
    pipeQueue2.grantConsumeMessages(filter2);
    pipeQueue3.grantConsumeMessages(filter3);
  }
}