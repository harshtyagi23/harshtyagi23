import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { EtlDataLakeStack } from '../lib/etl-data-lake-stack';

describe('EtlDataLakeStack', () => {
  it('creates raw and curated buckets plus ETL processor Lambda', () => {
    const app = new App();
    const stack = new EtlDataLakeStack(app, 'EtlDataLakeStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::S3::Bucket', 2);
    template.resourceCountIs('AWS::Lambda::Function', 1);
  });
});
