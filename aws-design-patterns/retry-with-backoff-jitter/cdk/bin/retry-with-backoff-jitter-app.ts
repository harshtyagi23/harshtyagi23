#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { RetryWithBackoffJitterStack } from '../lib/retry-with-backoff-jitter-stack';

const app = new cdk.App();
new RetryWithBackoffJitterStack(app, 'RetryWithBackoffJitterStack');
