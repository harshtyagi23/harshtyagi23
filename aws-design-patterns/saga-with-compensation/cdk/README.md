# Saga with Compensation CDK

This CDK app provisions a Step Functions workflow for a saga sequence with compensation:

- Forward steps: reserve inventory, capture payment, create shipment
- Compensation steps: refund payment, release inventory
- Catch path to compensation chain on failure

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
