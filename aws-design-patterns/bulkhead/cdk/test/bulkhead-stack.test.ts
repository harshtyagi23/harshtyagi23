import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { BulkheadStack } from '../lib/bulkhead-stack';

describe('BulkheadStack', () => {
  test('creates isolated queues and workers', () => {
    const app = new cdk.App();
    const stack = new BulkheadStack(app, 'TestBulkheadStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 2);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.resourceCountIs('AWS::Lambda::EventSourceMapping', 2);

    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      ReservedConcurrentExecutions: 50,
    });

    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      ReservedConcurrentExecutions: 10,
    });
  });
});
