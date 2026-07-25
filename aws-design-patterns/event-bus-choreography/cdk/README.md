# Event Bus Choreography CDK

Use this CDK app to provision an EventBridge custom bus with multiple rule-based consumers.

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

- One EventBridge custom event bus.
- Three Python Lambda consumers.
- Three EventBridge rules routing the same event to independent consumers.
