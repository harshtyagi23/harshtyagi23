import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { CacheAsideStack } from '../lib/cache-aside-stack';

describe('CacheAsideStack', () => {
  it('creates DynamoDB table and Python Lambda reader', () => {
    const app = new App();
    const stack = new CacheAsideStack(app, 'CacheAsideStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
      Environment: {
        Variables: {
          CACHE_TTL_SECONDS: '60',
        },
      },
    });
  });
});