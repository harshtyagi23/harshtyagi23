import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { IdempotencyConditionalWritesStack } from '../lib/idempotency-conditional-writes-stack';

describe('IdempotencyConditionalWritesStack', () => {
  it('creates a DynamoDB table and a Python Lambda consumer', () => {
    const app = new App();
    const stack = new IdempotencyConditionalWritesStack(app, 'IdempotencyConditionalWritesStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});