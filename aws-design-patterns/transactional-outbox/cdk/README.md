# Transactional Outbox CDK

Use this CDK app to provision order storage, outbox storage, and a relay queue for reliable event publication.

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

- One DynamoDB table for business order data.
- One DynamoDB table for outbox messages.
- One Lambda for write-side persistence.
- One Lambda for publishing outbox messages.
- One SQS queue as the relay target.
