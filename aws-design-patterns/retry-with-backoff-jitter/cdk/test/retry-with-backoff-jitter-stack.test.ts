import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { RetryWithBackoffJitterStack } from '../lib/retry-with-backoff-jitter-stack';

describe('RetryWithBackoffJitterStack', () => {
  test('creates queue, dlq, and worker', () => {
    const app = new cdk.App();
    const stack = new RetryWithBackoffJitterStack(app, 'TestRetryWithBackoffJitterStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 2);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::Lambda::EventSourceMapping', 1);

    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      Timeout: 15,
    });
  });
});
