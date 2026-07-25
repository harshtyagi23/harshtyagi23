# Circuit Breaker CDK

This CDK app deploys a lightweight reference for the Circuit Breaker pattern:

- DynamoDB table to store circuit state
- Lambda function that enforces or simulates breaker decisions
- API Gateway endpoint to invoke the handler

## Commands

- Install dependencies:

```bash
npm install
```

- Run tests:

```bash
npm test -- --runInBand
```

- Synthesize CloudFormation:

```bash
npm run cdk -- synth
```
