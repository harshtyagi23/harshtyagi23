import { Template } from 'aws-cdk-lib/assertions';
import * as cdk from 'aws-cdk-lib';
import { CircuitBreakerStack } from '../lib/circuit-breaker-stack';

describe('CircuitBreakerStack', () => {
  test('provisions state table, lambda, and API', () => {
    const app = new cdk.App();
    const stack = new CircuitBreakerStack(app, 'TestCircuitBreakerStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::ApiGateway::RestApi', 1);

    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      Timeout: 10,
    });
  });
});
