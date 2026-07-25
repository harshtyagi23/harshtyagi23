# ETL Data Lake CDK

Use this CDK app to provision raw and curated storage tiers plus an ETL Lambda processor.

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

- One S3 bucket for raw landing-zone data.
- One S3 bucket for curated output data.
- One Python Lambda ETL processor with raw-read and curated-write permissions.
