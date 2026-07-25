#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { StreamingIngestionStack } from '../lib/streaming-ingestion-stack';

const app = new App();
new StreamingIngestionStack(app, 'StreamingIngestionStack');
