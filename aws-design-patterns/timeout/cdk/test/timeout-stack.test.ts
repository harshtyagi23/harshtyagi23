import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { TimeoutStack } from '../lib/timeout-stack';

describe('TimeoutStack', () => {
  test('creates Lambda and API with timeout settings', () => {
    const app = new cdk.App();
    const stack = new TimeoutStack(app, 'TestTimeoutStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::ApiGateway::RestApi', 1);

    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      Timeout: 3,
      Environment: {
        Variables: {
          DOWNSTREAM_TIMEOUT_MS: '250',
          FALLBACK_MODE: 'cached-response',
        },
      },
    });
  });
});
