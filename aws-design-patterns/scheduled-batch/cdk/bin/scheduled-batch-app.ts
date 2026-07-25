#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { ScheduledBatchStack } from '../lib/scheduled-batch-stack';

const app = new App();
new ScheduledBatchStack(app, 'ScheduledBatchStack');