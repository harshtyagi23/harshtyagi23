# Bulkhead CDK

This CDK app provisions isolated worker lanes for two segments:

- Premium SQS queue and Lambda worker
- Standard SQS queue and Lambda worker
- Independent reserved concurrency values per worker

## Commands

- Install dependencies:

```bash
npm install
```

- Run tests:

```bash
npm test -- --runInBand
```

- Synthesize:

```bash
npm run cdk -- synth
```
