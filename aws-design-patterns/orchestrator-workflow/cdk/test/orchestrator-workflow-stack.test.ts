import { App } from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { OrchestratorWorkflowStack } from '../lib/orchestrator-workflow-stack';

describe('OrchestratorWorkflowStack', () => {
  it('creates workflow Lambdas and Step Functions state machine', () => {
    const app = new App();
    const stack = new OrchestratorWorkflowStack(app, 'OrchestratorWorkflowStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 4);
    template.resourceCountIs('AWS::StepFunctions::StateMachine', 1);
    template.hasResourceProperties('AWS::StepFunctions::StateMachine', {
      StateMachineName: 'order-orchestrator-workflow',
      DefinitionString: Match.anyValue(),
    });
  });
});
