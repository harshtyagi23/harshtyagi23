#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { RollingDeploymentStack } from '../lib/rolling-deployment-stack';
const app = new cdk.App();
new RollingDeploymentStack(app, 'RollingDeploymentStack');
