# Rate Limiting and Throttling CDK

Use this CDK app to provision an API Gateway + Lambda sample with usage-plan throttling.

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

- API Gateway REST API with `/api/process` and `/api/stats` endpoints.
- Lambda handlers for processing requests and returning limiter stats.
- API key and usage plan with rate, burst, and quota limits.
- CloudWatch alarms for API and Lambda health signals.