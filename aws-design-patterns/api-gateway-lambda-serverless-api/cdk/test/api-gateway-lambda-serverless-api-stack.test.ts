import { App } from 'aws-cdk-lib';
import { Match, Template } from 'aws-cdk-lib/assertions';
import { ApiGatewayLambdaServerlessApiStack } from '../lib/api-gateway-lambda-serverless-api-stack';

describe('ApiGatewayLambdaServerlessApiStack', () => {
  it('creates a REST API with Lambda integration', () => {
    const app = new App();
    const stack = new ApiGatewayLambdaServerlessApiStack(app, 'ApiGatewayLambdaServerlessApiStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::ApiGateway::RestApi', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
    template.hasResourceProperties('AWS::ApiGateway::Method', {
      HttpMethod: Match.stringLikeRegexp('GET|POST'),
      Integration: Match.objectLike({
        Type: 'AWS_PROXY',
      }),
    });
  });
});