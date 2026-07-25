import { Stack, StackProps } from 'aws-cdk-lib';
import * as apigateway from 'aws-cdk-lib/aws-apigateway';
import * as dynamodb from 'aws-cdk-lib/aws-dynamodb';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import { Construct } from 'constructs';

export class CqrsReadSideSearchOffloadingStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const writeTable = new dynamodb.Table(this, 'WriteModelTable', {
      partitionKey: { name: 'productId', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
    });

    const searchTable = new dynamodb.Table(this, 'SearchProjectionTable', {
      partitionKey: { name: 'productId', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
    });

    const writeFn = new lambda.Function(this, 'WriteModelHandlerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.write_handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        WRITE_TABLE_NAME: writeTable.tableName,
        SEARCH_TABLE_NAME: searchTable.tableName,
      },
    });

    const searchFn = new lambda.Function(this, 'SearchReadHandlerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.search_handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        SEARCH_TABLE_NAME: searchTable.tableName,
      },
    });

    writeTable.grantReadWriteData(writeFn);
    searchTable.grantReadWriteData(writeFn);
    searchTable.grantReadData(searchFn);

    const api = new apigateway.RestApi(this, 'SearchReadApi', {
      restApiName: 'CQRS Read Side Search API',
    });

    const searchResource = api.root.addResource('search');
    searchResource.addMethod('GET', new apigateway.LambdaIntegration(searchFn));
  }
}
