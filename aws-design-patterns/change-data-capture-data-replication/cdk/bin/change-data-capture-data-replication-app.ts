#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { ChangeDataCaptureDataReplicationStack } from '../lib/change-data-capture-data-replication-stack';

const app = new App();
new ChangeDataCaptureDataReplicationStack(app, 'ChangeDataCaptureDataReplicationStack');
