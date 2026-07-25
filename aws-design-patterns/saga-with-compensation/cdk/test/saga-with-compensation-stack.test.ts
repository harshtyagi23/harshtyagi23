import * as cdk from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { SagaWithCompensationStack } from '../lib/saga-with-compensation-stack';

describe('SagaWithCompensationStack', () => {
  test('creates a state machine with lambda steps and compensation path', () => {
    const app = new cdk.App();
    const stack = new SagaWithCompensationStack(app, 'TestSagaWithCompensationStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 5);
    template.resourceCountIs('AWS::StepFunctions::StateMachine', 1);

    template.hasResourceProperties('AWS::StepFunctions::StateMachine', {
      DefinitionString: Match.anyValue(),
    });
  });
});
