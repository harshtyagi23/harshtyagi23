#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { SecretsConfigExternalizationStack } from '../lib/secrets-config-externalization-stack';

const app = new App();
new SecretsConfigExternalizationStack(app, 'SecretsConfigExternalizationStack');