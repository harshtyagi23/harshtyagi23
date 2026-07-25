# Idempotency via Conditional Writes CDK

Use this CDK app to provision the DynamoDB idempotency table and the Lambda consumer for the idempotency example.

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

- One DynamoDB table for idempotency keys.
- One Python Lambda consumer that writes conditional idempotency records.
- IAM permissions for the consumer to write to the table.