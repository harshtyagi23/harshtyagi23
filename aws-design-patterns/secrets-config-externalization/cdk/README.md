# Secrets Config Externalization CDK

Use this CDK app to provision a secret, a parameter, and a Lambda function with least-privilege read access.

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

- One Secrets Manager secret for credentials.
- One SSM Parameter Store string parameter for non-secret config.
- One Python Lambda function with read permissions to both.