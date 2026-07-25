# CQRS Read Side Search Offloading CDK

Use this CDK app to provision separate write and read-side storage plus a read API for search-style queries.

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

- One DynamoDB table representing the write model.
- One DynamoDB table representing the search/read projection.
- One Lambda for write-side update + projection logic.
- One Lambda behind API Gateway for search queries.
