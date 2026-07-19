# DLQ + Redrive CDK

Use this CDK app to provision the primary SQS queue, the dead-letter queue, and the Lambda consumer for the DLQ + Redrive example.

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

- One primary SQS queue for normal processing.
- One SQS dead-letter queue for failed messages.
- One Python Lambda consumer attached to the primary queue.
- A redrive policy that moves poison messages into the DLQ.