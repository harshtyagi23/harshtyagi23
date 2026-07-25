import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { CanaryDeploymentStack } from '../lib/canary-deployment-stack';

describe('CanaryDeploymentStack', () => {
  test('creates lambda alias and canary codedeploy deployment group', () => {
    const app = new cdk.App();
    const stack = new CanaryDeploymentStack(app, 'TestCanaryDeploymentStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::Lambda::Alias', 1);
    template.resourceCountIs('AWS::CodeDeploy::DeploymentGroup', 1);

    template.hasResourceProperties('AWS::CodeDeploy::DeploymentGroup', {
      AutoRollbackConfiguration: {
        Enabled: true,
      },
      DeploymentConfigName: 'CodeDeployDefault.LambdaCanary10Percent5Minutes',
    });
  });
});
