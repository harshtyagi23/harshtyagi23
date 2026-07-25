# Sidecar CDK

Use this CDK app to provision an ECS Fargate service with an application container and a sidecar container in the same task.

## Prerequisites

- Node.js installed locally.
- AWS CDK bootstrap completed in the target account and region.
- AWS credentials or profile available in your shell.

## Commands

```bash
npm install
npm test -- --runInBand
npm run build
npm run cdk -- synth
```

## What this stack creates

- One VPC and ECS cluster.
- One Fargate task definition with an app container and a sidecar container.
- One Fargate service running the task definition.
