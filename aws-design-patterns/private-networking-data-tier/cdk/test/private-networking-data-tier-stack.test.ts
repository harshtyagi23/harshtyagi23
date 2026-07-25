import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { PrivateNetworkingDataTierStack } from '../lib/private-networking-data-tier-stack';

describe('PrivateNetworkingDataTierStack', () => {
  it('creates VPC, Lambda, and private RDS data tier resources', () => {
    const app = new App();
    const stack = new PrivateNetworkingDataTierStack(app, 'PrivateNetworkingDataTierStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::EC2::VPC', 1);
    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::RDS::DBInstance', 1);
  });
});
