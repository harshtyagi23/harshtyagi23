#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { IdempotencyConditionalWritesStack } from '../lib/idempotency-conditional-writes-stack';

const app = new App();
new IdempotencyConditionalWritesStack(app, 'IdempotencyConditionalWritesStack');