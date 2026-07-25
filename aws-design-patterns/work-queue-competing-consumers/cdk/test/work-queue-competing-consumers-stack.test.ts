import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { WorkQueueCompetingConsumersStack } from '../lib/work-queue-competing-consumers-stack';

describe('WorkQueueCompetingConsumersStack', () => {
  it('creates one queue and two competing Python Lambda workers', () => {
    const app = new App();
    const stack = new WorkQueueCompetingConsumersStack(app, 'WorkQueueCompetingConsumersStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 1);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});