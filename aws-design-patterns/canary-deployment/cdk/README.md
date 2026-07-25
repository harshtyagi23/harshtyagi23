# Canary Deployment CDK

This CDK app provisions a Lambda canary deployment setup with CodeDeploy:

- Lambda function plus live alias
- CodeDeploy deployment group using canary traffic shift
- Auto rollback on failed or stopped deployments

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
