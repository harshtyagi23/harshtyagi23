import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as apigateway from 'aws-cdk-lib/aws-apigateway';
import * as lambda from 'aws-cdk-lib/aws-lambda';

export class TimeoutStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const handler = new lambda.Function(this, 'TimeoutHandler', {
      runtime: lambda.Runtime.PYTHON_3_12,
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'timeout-pattern-ok'}\n",
      ),
      handler: 'index.handler',
      timeout: cdk.Duration.seconds(3),
      environment: {
        DOWNSTREAM_TIMEOUT_MS: '250',
        FALLBACK_MODE: 'cached-response',
      },
    });

    const api = new apigateway.RestApi(this, 'TimeoutApi', {
      restApiName: 'TimeoutApi',
      deployOptions: {
        stageName: 'prod',
      },
    });

    const integration = new apigateway.LambdaIntegration(handler);
    api.root.addResource('profile').addMethod('POST', integration);

    new cdk.CfnOutput(this, 'ApiUrl', {
      value: api.url,
    });
  }
}
