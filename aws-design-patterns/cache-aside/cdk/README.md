# Cache-Aside CDK

Use this CDK app to provision a DynamoDB table and Lambda function for cache-aside read logic.

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

- One DynamoDB table as the source of truth.
- One Python Lambda reader implementing cache-aside logic.
- Read permissions from Lambda to DynamoDB.