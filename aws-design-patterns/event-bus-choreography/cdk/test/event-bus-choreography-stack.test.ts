import { App } from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { EventBusChoreographyStack } from '../lib/event-bus-choreography-stack';

describe('EventBusChoreographyStack', () => {
  it('creates an event bus, three consumer Lambdas, and routing rules', () => {
    const app = new App();
    const stack = new EventBusChoreographyStack(app, 'EventBusChoreographyStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Events::EventBus', 1);
    template.resourceCountIs('AWS::Lambda::Function', 3);
    template.resourceCountIs('AWS::Events::Rule', 3);
    template.hasResourceProperties('AWS::Events::EventBus', {
      Name: 'orders-choreography-bus',
    });
    template.hasResourceProperties('AWS::Events::Rule', {
      EventPattern: {
        source: ['app.orders'],
        'detail-type': ['OrderCreated'],
      },
      Targets: Match.anyValue(),
    });
  });
});
