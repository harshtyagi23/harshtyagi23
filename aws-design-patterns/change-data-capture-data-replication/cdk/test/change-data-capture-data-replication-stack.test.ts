import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { ChangeDataCaptureDataReplicationStack } from '../lib/change-data-capture-data-replication-stack';

describe('ChangeDataCaptureDataReplicationStack', () => {
  it('creates source storage, capture/replicate functions, and replication queue', () => {
    const app = new App();
    const stack = new ChangeDataCaptureDataReplicationStack(app, 'ChangeDataCaptureDataReplicationStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::DynamoDB::Table', 1);
    template.resourceCountIs('AWS::Lambda::Function', 2);
    template.resourceCountIs('AWS::SQS::Queue', 1);
  });
});
