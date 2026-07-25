#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { StranglerFacadeStack } from '../lib/strangler-facade-stack';
const app = new cdk.App();
new StranglerFacadeStack(app, 'StranglerFacadeStack');
