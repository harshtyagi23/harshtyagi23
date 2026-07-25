import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as codedeploy from 'aws-cdk-lib/aws-codedeploy';
import * as lambda from 'aws-cdk-lib/aws-lambda';

export class BlueGreenDeploymentStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const fn = new lambda.Function(this, 'BlueGreenHandler', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'index.handler',
      code: lambda.Code.fromInline(
        "def handler(event, context):\n" +
          "    return {'statusCode': 200, 'body': 'blue-green-ok'}\n",
      ),
      timeout: cdk.Duration.seconds(10),
    });

    const alias = new lambda.Alias(this, 'LiveAlias', {
      aliasName: 'live',
      version: fn.currentVersion,
    });

    const deploymentGroup = new codedeploy.LambdaDeploymentGroup(this, 'DeploymentGroup', {
      alias,
      deploymentConfig: codedeploy.LambdaDeploymentConfig.LINEAR_10PERCENT_EVERY_1MINUTE,
      autoRollback: {
        failedDeployment: true,
        stoppedDeployment: true,
      },
    });

    new cdk.CfnOutput(this, 'AliasArn', { value: alias.functionArn });
    new cdk.CfnOutput(this, 'DeploymentGroupName', { value: deploymentGroup.deploymentGroupName });
  }
}
