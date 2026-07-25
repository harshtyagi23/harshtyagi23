import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { BlueGreenDeploymentStack } from '../lib/blue-green-deployment-stack';

describe('BlueGreenDeploymentStack', () => {
  test('creates lambda, alias, and codedeploy deployment group', () => {
    const app = new cdk.App();
    const stack = new BlueGreenDeploymentStack(app, 'TestBlueGreenDeploymentStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::Lambda::Alias', 1);
    template.resourceCountIs('AWS::CodeDeploy::DeploymentGroup', 1);

    template.hasResourceProperties('AWS::CodeDeploy::DeploymentGroup', {
      AutoRollbackConfiguration: {
        Enabled: true,
      },
    });
  });
});
