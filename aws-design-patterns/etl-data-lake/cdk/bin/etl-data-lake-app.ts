#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { EtlDataLakeStack } from '../lib/etl-data-lake-stack';

const app = new App();
new EtlDataLakeStack(app, 'EtlDataLakeStack');
