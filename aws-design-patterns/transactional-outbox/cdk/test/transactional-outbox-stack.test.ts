import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { TransactionalOutboxStack } from '../lib/transactional-outbox-stack';

describe('TransactionalOutboxStack', () => {
  it('creates orders and outbox storage plus a relay queue', () => {
    const app = new App();
    const stack = new TransactionalOutboxStack(app, 'TransactionalOutboxStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 2);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.resourceCountIs('AWS::SQS::Queue', 1);
  });
});
