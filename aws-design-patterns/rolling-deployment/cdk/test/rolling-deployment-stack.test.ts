import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { RollingDeploymentStack } from '../lib/rolling-deployment-stack';

test('creates ecs service for rolling deployments', () => {
  const app = new cdk.App();
  const stack = new RollingDeploymentStack(app, 'TestRollingDeploymentStack');
  const template = Template.fromStack(stack);
  template.resourceCountIs('AWS::ECS::Service', 1);
});
