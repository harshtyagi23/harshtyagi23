#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { EventDrivenFileProcessingStack } from '../lib/event-driven-file-processing-stack';

const app = new App();
new EventDrivenFileProcessingStack(app, 'EventDrivenFileProcessingStack');