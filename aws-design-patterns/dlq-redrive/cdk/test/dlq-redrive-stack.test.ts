import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { DlqRedriveStack } from '../lib/dlq-redrive-stack';

describe('DlqRedriveStack', () => {
  it('creates a primary queue, DLQ, and Python Lambda consumer', () => {
    const app = new App();
    const stack = new DlqRedriveStack(app, 'DlqRedriveStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 2);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});