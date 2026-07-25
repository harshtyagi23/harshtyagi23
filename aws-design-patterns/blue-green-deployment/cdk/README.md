# Blue Green Deployment CDK

This CDK app deploys a Lambda blue/green release setup using CodeDeploy:

- Lambda function and `live` alias
- CodeDeploy deployment group with linear traffic shifting
- Auto-rollback enabled for failed deployments and alarms

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
