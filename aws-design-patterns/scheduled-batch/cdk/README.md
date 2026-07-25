# Scheduled Batch CDK

Use this CDK app to provision a scheduled EventBridge rule that triggers a Lambda batch worker.

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

- One Python Lambda batch worker.
- One EventBridge rule using a rate schedule.
- Lambda invocation permission from EventBridge.