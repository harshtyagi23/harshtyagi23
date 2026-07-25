# Event-Driven File Processing CDK

Use this CDK app to provision an S3 bucket and a Lambda processor wired to `ObjectCreated` events.

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

- One S3 bucket for uploaded files.
- One Python Lambda function for file processing.
- S3 bucket notification to invoke Lambda on object creation.