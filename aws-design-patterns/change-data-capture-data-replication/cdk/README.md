# Change Data Capture Data Replication CDK

Use this CDK app to provision a source table with change stream semantics, capture/replication functions, and a relay queue.

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

- One DynamoDB table acting as the source system with streams enabled.
- One Lambda function for change capture.
- One SQS queue for replication handoff.
- One Lambda function for applying replicated changes downstream.
