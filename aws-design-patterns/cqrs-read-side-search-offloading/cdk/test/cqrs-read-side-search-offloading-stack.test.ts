import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { CqrsReadSideSearchOffloadingStack } from '../lib/cqrs-read-side-search-offloading-stack';

describe('CqrsReadSideSearchOffloadingStack', () => {
  it('creates separate write and search tables plus search API', () => {
    const app = new App();
    const stack = new CqrsReadSideSearchOffloadingStack(app, 'CqrsReadSideSearchOffloadingStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 2);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.resourceCountIs('AWS::ApiGateway::RestApi', 1);
  });
});
