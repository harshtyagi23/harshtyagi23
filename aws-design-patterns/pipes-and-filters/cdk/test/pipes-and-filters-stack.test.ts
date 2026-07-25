import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { PipesAndFiltersStack } from '../lib/pipes-and-filters-stack';

describe('PipesAndFiltersStack', () => {
  it('creates three queues and three Python filter lambdas', () => {
    const app = new App();
    const stack = new PipesAndFiltersStack(app, 'PipesAndFiltersStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::SQS::Queue', 3);
    template.resourceCountIs('AWS::Lambda::Function', 3);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});