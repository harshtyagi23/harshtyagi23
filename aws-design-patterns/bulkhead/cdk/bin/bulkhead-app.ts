#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { BulkheadStack } from '../lib/bulkhead-stack';

const app = new cdk.App();
new BulkheadStack(app, 'BulkheadStack');
