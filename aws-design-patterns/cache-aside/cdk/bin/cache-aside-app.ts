#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { CacheAsideStack } from '../lib/cache-aside-stack';

const app = new App();
new CacheAsideStack(app, 'CacheAsideStack');