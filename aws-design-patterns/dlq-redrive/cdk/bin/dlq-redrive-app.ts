#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { DlqRedriveStack } from '../lib/dlq-redrive-stack';

const app = new App();
new DlqRedriveStack(app, 'DlqRedriveStack');