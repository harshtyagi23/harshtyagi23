#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { EventBusChoreographyStack } from '../lib/event-bus-choreography-stack';

const app = new App();
new EventBusChoreographyStack(app, 'EventBusChoreographyStack');
