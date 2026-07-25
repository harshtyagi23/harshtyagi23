import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as route53 from 'aws-cdk-lib/aws-route53';

export class MultiRegionFailoverStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    const zone = new route53.PublicHostedZone(this, 'Zone', { zoneName: 'example-failover.local' });
    new route53.CfnHealthCheck(this, 'PrimaryHealth', {
      healthCheckConfig: { type: 'HTTPS', fullyQualifiedDomainName: 'primary.example.com', requestInterval: 30, failureThreshold: 3 },
    });
    new cdk.CfnOutput(this, 'ZoneId', { value: zone.hostedZoneId });
  }
}
