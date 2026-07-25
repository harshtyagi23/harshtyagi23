#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { TimeoutStack } from '../lib/timeout-stack';

const app = new cdk.App();
new TimeoutStack(app, 'TimeoutStack');
