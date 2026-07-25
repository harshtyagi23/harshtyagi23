#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { QueueBasedLoadLevelingStack } from '../lib/queue-based-load-leveling-stack';

const app = new App();
new QueueBasedLoadLevelingStack(app, 'QueueBasedLoadLevelingStack');