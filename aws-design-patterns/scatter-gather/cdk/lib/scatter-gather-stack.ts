import { Stack, StackProps } from 'aws-cdk-lib';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as sfn from 'aws-cdk-lib/aws-stepfunctions';
import * as tasks from 'aws-cdk-lib/aws-stepfunctions-tasks';
import { Construct } from 'constructs';

export class ScatterGatherStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const catalogFn = new lambda.Function(this, 'CatalogWorkerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.catalog_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const reviewsFn = new lambda.Function(this, 'ReviewsWorkerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.reviews_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const pricingFn = new lambda.Function(this, 'PricingWorkerFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.pricing_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const gatherFn = new lambda.Function(this, 'GatherResultsFn', {
      runtime: lambda.Runtime.PYTHON_3_12,
      handler: 'lambda_handlers.gather_handler',
      code: lambda.Code.fromAsset('../example'),
    });

    const catalogTask = new tasks.LambdaInvoke(this, 'CatalogBranch', {
      lambdaFunction: catalogFn,
      outputPath: '$.Payload',
    });

    const reviewsTask = new tasks.LambdaInvoke(this, 'ReviewsBranch', {
      lambdaFunction: reviewsFn,
      outputPath: '$.Payload',
    });

    const pricingTask = new tasks.LambdaInvoke(this, 'PricingBranch', {
      lambdaFunction: pricingFn,
      outputPath: '$.Payload',
    });

    const parallel = new sfn.Parallel(this, 'ScatterQueries')
      .branch(catalogTask)
      .branch(reviewsTask)
      .branch(pricingTask);

    const gatherTask = new tasks.LambdaInvoke(this, 'GatherResults', {
      lambdaFunction: gatherFn,
      outputPath: '$.Payload',
    });

    const definition = parallel.next(gatherTask).next(new sfn.Succeed(this, 'QueryComplete'));

    new sfn.StateMachine(this, 'ScatterGatherStateMachine', {
      definitionBody: sfn.DefinitionBody.fromChainable(definition),
      stateMachineName: 'scatter-gather-search-workflow',
    });
  }
}
