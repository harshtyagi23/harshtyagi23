# Timeout CDK

This CDK app provisions a minimal timeout reference architecture:

- Lambda function configured with a strict timeout budget
- API Gateway endpoint to invoke timeout-aware logic
- Environment variables for timeout and fallback policy

## Commands

- Install:

```bash
npm install
```

- Test:

```bash
npm test -- --runInBand
```

- Synthesize:

```bash
npm run cdk -- synth
```
