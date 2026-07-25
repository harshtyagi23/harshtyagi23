# Streaming Ingestion CDK

Use this CDK app to provision a Kinesis stream and Lambda consumer for continuous event ingestion.

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

- One Kinesis Data Stream.
- One Python Lambda stream processor.
- One Lambda event source mapping to consume batches from the stream.
