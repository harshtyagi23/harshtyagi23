import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { StreamingIngestionStack } from '../lib/streaming-ingestion-stack';

describe('StreamingIngestionStack', () => {
  it('creates a Kinesis stream and Lambda event source mapping', () => {
    const app = new App();
    const stack = new StreamingIngestionStack(app, 'StreamingIngestionStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Kinesis::Stream', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::Lambda::EventSourceMapping', 1);
  });
});
