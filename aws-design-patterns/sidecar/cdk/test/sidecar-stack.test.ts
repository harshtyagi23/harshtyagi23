import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { SidecarStack } from '../lib/sidecar-stack';

describe('SidecarStack', () => {
  it('creates an ECS task definition with app and sidecar containers', () => {
    const app = new App();
    const stack = new SidecarStack(app, 'SidecarStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::ECS::TaskDefinition', 1);
    template.resourceCountIs('AWS::ECS::Service', 1);
    template.resourceCountIs('AWS::ECS::Cluster', 1);
  });
});
