#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { BlueGreenDeploymentStack } from '../lib/blue-green-deployment-stack';

const app = new cdk.App();
new BlueGreenDeploymentStack(app, 'BlueGreenDeploymentStack');
