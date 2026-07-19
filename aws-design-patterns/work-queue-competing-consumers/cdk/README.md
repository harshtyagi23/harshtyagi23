# Work Queue / Competing Consumers CDK

Use this CDK app to provision the queue and two Lambda workers for the Work Queue / Competing Consumers example.

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

- One SQS queue for background jobs.
- Two Python Lambda workers that poll the same queue.
- Queue-driven event source mappings for each worker.