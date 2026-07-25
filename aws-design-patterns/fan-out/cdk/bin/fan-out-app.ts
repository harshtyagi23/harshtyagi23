#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { FanOutStack } from '../lib/fan-out-stack';

const app = new App();
new FanOutStack(app, 'FanOutStack');
