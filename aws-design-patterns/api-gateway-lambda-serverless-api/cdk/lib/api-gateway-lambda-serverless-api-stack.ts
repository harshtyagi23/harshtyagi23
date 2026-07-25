import { Stack, StackProps } from 'aws-cdk-lib';
import * as apigw from 'aws-cdk-lib/aws-apigateway';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import { Construct } from 'constructs';

export class ApiGatewayLambdaServerlessApiStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const handler = new lambda.Function(this, 'ApiHandler', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer.handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const api = new apigw.RestApi(this, 'OrdersApi', {
      restApiName: 'orders-api',
    });

    const health = api.root.addResource('health');
    health.addMethod('GET', new apigw.LambdaIntegration(handler));

    const orders = api.root.addResource('orders');
    orders.addMethod('POST', new apigw.LambdaIntegration(handler));
  }
}