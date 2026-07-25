#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { MultiRegionFailoverStack } from '../lib/multi-region-failover-stack';
const app = new cdk.App();
new MultiRegionFailoverStack(app, 'MultiRegionFailoverStack');
