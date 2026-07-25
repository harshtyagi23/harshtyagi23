import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { StranglerFacadeStack } from '../lib/strangler-facade-stack';

test('creates api gateway facade', () => {
  const app = new cdk.App();
  const stack = new StranglerFacadeStack(app, 'TestStranglerFacadeStack');
  const template = Template.fromStack(stack);
  template.resourceCountIs('AWS::ApiGateway::RestApi', 1);
});
