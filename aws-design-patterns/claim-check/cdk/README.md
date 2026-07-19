# Claim Check CDK

Use this CDK app to provision the S3 bucket, SQS queue, and Lambda consumer for the Claim Check example.

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

- One S3 bucket for full payload objects.
- One SQS queue that carries lightweight references.
- One Python Lambda consumer that reads references and fetches payloads.