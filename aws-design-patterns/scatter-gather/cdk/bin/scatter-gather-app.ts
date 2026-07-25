#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { ScatterGatherStack } from '../lib/scatter-gather-stack';

const app = new App();
new ScatterGatherStack(app, 'ScatterGatherStack');
