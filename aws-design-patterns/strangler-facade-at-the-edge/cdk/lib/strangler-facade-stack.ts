import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as apigw from 'aws-cdk-lib/aws-apigateway';

export class StranglerFacadeStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    const api = new apigw.RestApi(this, 'FacadeApi');
    api.root.addResource('v1').addProxy({ anyMethod: true });
    api.root.addResource('v2').addProxy({ anyMethod: true });
  }
}
