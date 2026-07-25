# Retry with Backoff Jitter CDK

This CDK app deploys a minimal retry-oriented processing topology:

- Primary SQS queue with dead-letter queue
- Lambda worker consuming from SQS
- Environment-driven retry policy settings for worker logic

## Commands

- Install dependencies:

```bash
npm install
```

- Run tests:

```bash
npm test -- --runInBand
```

- Synthesize template:

```bash
npm run cdk -- synth
```
