import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as codedeploy from 'aws-cdk-lib/aws-codedeploy';
import * as lambda from 'aws-cdk-lib/aws-lambda';

export class CanaryDeploymentStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const fn = new lambda.Function(this, 'CanaryHandler', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'canary-ok'}\n",
      ),
      timeout: cdk.Duration.seconds(10),
    });

    const alias = new lambda.Alias(this, 'LiveAlias', {
      aliasName: 'live',
      version: fn.currentVersion,
    });

    const deploymentGroup = new codedeploy.LambdaDeploymentGroup(this, 'CanaryDeploymentGroup', {
      alias,
      deploymentConfig: codedeploy.LambdaDeploymentConfig.CANARY_10PERCENT_5MINUTES,
      autoRollback: {
        failedDeployment: true,
        stoppedDeployment: true,
      },
    });

    new cdk.CfnOutput(this, 'AliasArn', { value: alias.functionArn });
    new cdk.CfnOutput(this, 'DeploymentGroupName', { value: deploymentGroup.deploymentGroupName });
  }
}
