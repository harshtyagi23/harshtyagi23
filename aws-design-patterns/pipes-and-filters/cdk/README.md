# Pipes and Filters CDK

Use this CDK app to provision an SQS-based three-stage Lambda processing pipeline.

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

- Three SQS queues acting as pipes.
- Three Python Lambda functions acting as filters.
- Event source mappings for each queue to its corresponding Lambda.