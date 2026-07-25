import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as secretsmanager from 'aws-cdk-lib/aws-secretsmanager';
import * as ssm from 'aws-cdk-lib/aws-ssm';
import { Construct } from 'constructs';

export class SecretsConfigExternalizationStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const appFn = new lambda.Function(this, 'ConfigLoaderFunction', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'consumer.handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        PARAMETER_NAME: '/sample/api/base-url',
      },
    });

    const dbSecret = new secretsmanager.Secret(this, 'DbCredentialsSecret', {
      generateSecretString: {
        secretStringTemplate: JSON.stringify({ username: 'app_user' }),
        generateStringKey: 'password',
      },
    });

    const apiBaseUrl = new ssm.StringParameter(this, 'ApiBaseUrlParameter', {
      parameterName: '/sample/api/base-url',
      stringValue: 'https://api.internal.example',
    });

    dbSecret.grantRead(appFn);
    apiBaseUrl.grantRead(appFn);
  }
}