# API Gateway Lambda Serverless API CDK

Use this CDK app to provision a REST API backed by a Python Lambda handler.

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

- One Python Lambda API handler.
- One API Gateway REST API.
- Route integrations for `/health` (GET) and `/orders` (POST).