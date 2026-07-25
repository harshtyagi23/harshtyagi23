import * as cdk from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { MultiRegionFailoverStack } from '../lib/multi-region-failover-stack';

test('creates route53 hosted zone and health check', () => {
  const app = new cdk.App();
  const stack = new MultiRegionFailoverStack(app, 'TestMultiRegionFailoverStack');
  const template = Template.fromStack(stack);
  template.resourceCountIs('AWS::Route53::HostedZone', 1);
  template.resourceCountIs('AWS::Route53::HealthCheck', 1);
});
