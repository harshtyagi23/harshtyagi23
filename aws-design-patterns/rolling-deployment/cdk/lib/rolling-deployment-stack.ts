import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as ecs from 'aws-cdk-lib/aws-ecs';

export class RollingDeploymentStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    const cluster = new ecs.Cluster(this, 'Cluster');
    const task = new ecs.FargateTaskDefinition(this, 'Task');
    task.addContainer('App', { image: ecs.ContainerImage.fromRegistry('public.ecr.aws/docker/library/nginx:latest') });
    new ecs.FargateService(this, 'Service', {
      cluster,
      taskDefinition: task,
      desiredCount: 3,
      deploymentController: { type: ecs.DeploymentControllerType.ECS },
      minHealthyPercent: 50,
      maxHealthyPercent: 200,
    });
  }
}
