import { Stack, StackProps } from 'aws-cdk-lib';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import { Construct } from 'constructs';

export class SidecarStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, 'SidecarVpc', { maxAzs: 2, natGateways: 1 });
    const cluster = new ecs.Cluster(this, 'SidecarCluster', { vpc });

    const taskDefinition = new ecs.FargateTaskDefinition(this, 'AppWithSidecarTask', {
      cpu: 512,
      memoryLimitMiB: 1024,
    });

    taskDefinition.addContainer('AppContainer', {
      image: ecs.ContainerImage.fromRegistry('public.ecr.aws/docker/library/nginx:latest'),
      portMappings: [{ containerPort: 80 }],
      essential: true,
    });

    taskDefinition.addContainer('LogSidecar', {
      image: ecs.ContainerImage.fromRegistry('public.ecr.aws/docker/library/busybox:latest'),
      command: ['sh', '-c', 'while true; do echo forwarding logs; sleep 30; done'],
      essential: false,
    });

    new ecs.FargateService(this, 'SidecarService', {
      cluster,
      taskDefinition,
      desiredCount: 1,
    });
  }
}
