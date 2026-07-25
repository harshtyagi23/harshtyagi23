#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { SagaWithCompensationStack } from '../lib/saga-with-compensation-stack';

const app = new cdk.App();
new SagaWithCompensationStack(app, 'SagaWithCompensationStack');
