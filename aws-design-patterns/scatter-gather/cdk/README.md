# Scatter Gather CDK

Use this CDK app to provision a Step Functions workflow that fans out parallel Lambda queries and gathers the responses.

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

- Three Python Lambda worker functions for parallel query branches.
- One Python Lambda aggregator for the gather step.
- One Step Functions state machine using a `Parallel` state and gather task.
