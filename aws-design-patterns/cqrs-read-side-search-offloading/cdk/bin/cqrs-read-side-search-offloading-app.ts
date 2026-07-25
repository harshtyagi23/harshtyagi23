#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { CqrsReadSideSearchOffloadingStack } from '../lib/cqrs-read-side-search-offloading-stack';

const app = new App();
new CqrsReadSideSearchOffloadingStack(app, 'CqrsReadSideSearchOffloadingStack');
