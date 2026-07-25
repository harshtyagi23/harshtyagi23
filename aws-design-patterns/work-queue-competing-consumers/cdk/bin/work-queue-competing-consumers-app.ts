#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { WorkQueueCompetingConsumersStack } from '../lib/work-queue-competing-consumers-stack';

const app = new App();
new WorkQueueCompetingConsumersStack(app, 'WorkQueueCompetingConsumersStack');