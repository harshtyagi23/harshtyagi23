# Fan Out CDK

Use this CDK app to provision the SNS topic, two SQS queues, and two Python Lambda consumers for the Fan-Out example.

## Prerequisites

- Node.js installed locally.
- AWS CDK bootstrap completed in the target account and region.
- An AWS profile or credentials available in your shell.

## Commands

```bash
npm install
npm test -- --runInBand
npm run build
npm run cdk -- synth
```

## What this stack creates

- One SNS topic for the fan-out event.
- Two SQS queues, one per consumer.
- Two Python Lambda functions that read from their own queue.
- SNS subscriptions that connect the topic to both queues.
