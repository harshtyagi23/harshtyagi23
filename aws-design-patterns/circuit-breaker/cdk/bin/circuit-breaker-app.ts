#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { CircuitBreakerStack } from '../lib/circuit-breaker-stack';

const app = new cdk.App();
new CircuitBreakerStack(app, 'CircuitBreakerStack');
