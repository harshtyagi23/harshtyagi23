import { App } from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { ScheduledBatchStack } from '../lib/scheduled-batch-stack';

describe('ScheduledBatchStack', () => {
  it('creates a Python Lambda and scheduled EventBridge rule', () => {
    const app = new App();
    const stack = new ScheduledBatchStack(app, 'ScheduledBatchStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::Events::Rule', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
    template.hasResourceProperties('AWS::Events::Rule', {
      ScheduleExpression: Match.stringLikeRegexp('rate\\(5 minutes\\)'),
    });
  });
});