#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { TransactionalOutboxStack } from '../lib/transactional-outbox-stack';

const app = new App();
new TransactionalOutboxStack(app, 'TransactionalOutboxStack');
