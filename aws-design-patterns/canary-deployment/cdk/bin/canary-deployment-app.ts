#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { CanaryDeploymentStack } from '../lib/canary-deployment-stack';

const app = new cdk.App();
new CanaryDeploymentStack(app, 'CanaryDeploymentStack');
