import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as apigateway from 'aws-cdk-lib/aws-apigateway';
import * as dynamodb from 'aws-cdk-lib/aws-dynamodb';
import * as lambda from 'aws-cdk-lib/aws-lambda';

export class CircuitBreakerStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const stateTable = new dynamodb.Table(this, 'CircuitStateTable', {
      partitionKey: { name: 'circuitId', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
      removalPolicy: cdk.RemovalPolicy.DESTROY,
    });

    const handler = new lambda.Function(this, 'CircuitHandler', {
      runtime: lambda.Runtime.PYTHON_3_12,
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'circuit-breaker-ok'}\n",
      ),
      handler: 'index.handler',
      timeout: cdk.Duration.seconds(10),
      environment: {
        CIRCUIT_STATE_TABLE: stateTable.tableName,
        FAILURE_THRESHOLD: '3',
        RECOVERY_TIMEOUT_SECONDS: '30',
      },
    });

    stateTable.grantReadWriteData(handler);

    const api = new apigateway.RestApi(this, 'CircuitBreakerApi', {
      restApiName: 'CircuitBreakerApi',
      deployOptions: {
        stageName: 'prod',
      },
    });

    const invoke = new apigateway.LambdaIntegration(handler);
    api.root.addResource('invoke').addMethod('POST', invoke);

    new cdk.CfnOutput(this, 'ApiUrl', {
      value: api.url,
    });
    new cdk.CfnOutput(this, 'StateTableName', {
      value: stateTable.tableName,
    });
  }
}
