import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { FanOutStack } from '../lib/fan-out-stack';

describe('FanOutStack', () => {
  it('creates the fan-out topic, queues, and lambda consumers', () => {
    const app = new App();
    const stack = new FanOutStack(app, 'FanOutStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SNS::Topic', 1);
    template.resourceCountIs('AWS::SQS::Queue', 2);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});