# Orchestrator Workflow CDK

Use this CDK app to provision a Step Functions workflow that orchestrates order processing steps with Lambda tasks.

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

- Four Python Lambda functions for validation, inventory, payment, and shipping steps.
- One Step Functions state machine with explicit success/failure paths.
- Invocation permissions between Step Functions and Lambda tasks.
