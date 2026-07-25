import { Stack, StackProps } from 'aws-cdk-lib';
import * as events from 'aws-cdk-lib/aws-events';
import * as targets from 'aws-cdk-lib/aws-events-targets';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import { Construct } from 'constructs';

export class EventBusChoreographyStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const eventBus = new events.EventBus(this, 'OrdersEventBus', {
      eventBusName: 'orders-choreography-bus',
    });

    const inventoryFn = new lambda.Function(this, 'InventoryConsumerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.inventory_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const billingFn = new lambda.Function(this, 'BillingConsumerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.billing_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const notificationFn = new lambda.Function(this, 'NotificationConsumerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.notification_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const orderCreatedPattern = {
      source: ['app.orders'],
      detailType: ['OrderCreated'],
    };

    new events.Rule(this, 'InventoryRule', {
      eventBus,
      eventPattern: orderCreatedPattern,
      targets: [new targets.LambdaFunction(inventoryFn)],
    });

    new events.Rule(this, 'BillingRule', {
      eventBus,
      eventPattern: orderCreatedPattern,
      targets: [new targets.LambdaFunction(billingFn)],
    });

    new events.Rule(this, 'NotificationRule', {
      eventBus,
      eventPattern: orderCreatedPattern,
      targets: [new targets.LambdaFunction(notificationFn)],
    });
  }
}
