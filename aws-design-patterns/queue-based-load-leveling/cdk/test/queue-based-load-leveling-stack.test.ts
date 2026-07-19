import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { QueueBasedLoadLevelingStack } from '../lib/queue-based-load-leveling-stack';

describe('QueueBasedLoadLevelingStack', () => {
  it('creates an SQS queue and a Python Lambda consumer', () => {
    const app = new App();
    const stack = new QueueBasedLoadLevelingStack(app, 'QueueBasedLoadLevelingStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});