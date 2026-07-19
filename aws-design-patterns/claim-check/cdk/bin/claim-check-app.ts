#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { ClaimCheckStack } from '../lib/claim-check-stack';

const app = new App();
new ClaimCheckStack(app, 'ClaimCheckStack');