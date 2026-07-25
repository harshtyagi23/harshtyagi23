#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { PrivateNetworkingDataTierStack } from '../lib/private-networking-data-tier-stack';

const app = new App();
new PrivateNetworkingDataTierStack(app, 'PrivateNetworkingDataTierStack');
