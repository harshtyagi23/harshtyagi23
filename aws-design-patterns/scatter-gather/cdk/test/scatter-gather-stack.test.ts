import { App } from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { ScatterGatherStack } from '../lib/scatter-gather-stack';

describe('ScatterGatherStack', () => {
  it('creates worker Lambdas and a Step Functions parallel workflow', () => {
    const app = new App();
    const stack = new ScatterGatherStack(app, 'ScatterGatherStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 4);
    template.resourceCountIs('AWS::StepFunctions::StateMachine', 1);
    template.hasResourceProperties('AWS::StepFunctions::StateMachine', {
      StateMachineName: 'scatter-gather-search-workflow',
      DefinitionString: Match.anyValue(),
    });
  });
});
