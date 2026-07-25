import { Stack, StackProps } from 'aws-cdk-lib';
import * as dynamodb from 'aws-cdk-lib/aws-dynamodb';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sqs from 'aws-cdk-lib/aws-sqs';
import { Construct } from 'constructs';

export class TransactionalOutboxStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const ordersTable = new dynamodb.Table(this, 'OrdersTable', {
      partitionKey: { name: 'orderId', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
    });

    const outboxTable = new dynamodb.Table(this, 'OutboxTable', {
      partitionKey: { name: 'orderId', type: dynamodb.AttributeType.STRING },
      sortKey: { name: 'eventType', type: dynamodb.AttributeType.STRING },
      billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
    });

    const relayQueue = new sqs.Queue(this, 'OutboxRelayQueue');

    const writeFn = new lambda.Function(this, 'OutboxWriteFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.write_handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        ORDERS_TABLE_NAME: ordersTable.tableName,
        OUTBOX_TABLE_NAME: outboxTable.tableName,
      },
    });

    const relayFn = new lambda.Function(this, 'OutboxRelayFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'handlers.publisher_handler',
      code: lambda.Code.fromAsset('../example'),
      environment: {
        RELAY_QUEUE_URL: relayQueue.queueUrl,
      },
    });

    ordersTable.grantReadWriteData(writeFn);
    outboxTable.grantReadWriteData(writeFn);
    outboxTable.grantReadData(relayFn);
    relayQueue.grantSendMessages(relayFn);
  }
}
