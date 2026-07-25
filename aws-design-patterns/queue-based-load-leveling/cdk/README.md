# Queue Based Load Leveling CDK

Use this CDK app to provision the SQS queue and Lambda consumer for the Queue-Based Load Leveling example.

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

- One SQS queue for buffered work.
- One Python Lambda consumer that polls the queue.
- SQS event source mapping for queue-driven processing.