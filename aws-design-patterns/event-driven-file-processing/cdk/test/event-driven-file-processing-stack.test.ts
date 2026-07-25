import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { EventDrivenFileProcessingStack } from '../lib/event-driven-file-processing-stack';

describe('EventDrivenFileProcessingStack', () => {
  it('creates an S3 bucket and a Python Lambda processor', () => {
    const app = new App();
    const stack = new EventDrivenFileProcessingStack(app, 'EventDrivenFileProcessingStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::S3::Bucket', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});