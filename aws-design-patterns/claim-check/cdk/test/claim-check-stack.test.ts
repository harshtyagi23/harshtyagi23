import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { ClaimCheckStack } from '../lib/claim-check-stack';

describe('ClaimCheckStack', () => {
  it('creates one S3 bucket, one SQS queue, and one Python Lambda consumer', () => {
    const app = new App();
    const stack = new ClaimCheckStack(app, 'ClaimCheckStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::S3::Bucket', 1);
    template.resourceCountIs('AWS::SQS::Queue', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});