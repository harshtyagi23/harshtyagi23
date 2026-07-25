# Private Networking Data Tier CDK

Use this CDK app to provision an application Lambda in private subnets with an isolated RDS data tier.

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

- One VPC with public, private-app, and isolated private-data subnets.
- One application Lambda placed in private app subnets.
- One PostgreSQL RDS instance in isolated private subnets.
- Security groups that allow only the app tier to reach the data tier.
