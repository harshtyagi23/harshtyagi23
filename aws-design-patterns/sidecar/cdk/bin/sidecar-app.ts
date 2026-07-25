#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { SidecarStack } from '../lib/sidecar-stack';

const app = new App();
new SidecarStack(app, 'SidecarStack');
