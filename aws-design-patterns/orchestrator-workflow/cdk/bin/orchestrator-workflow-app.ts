#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { OrchestratorWorkflowStack } from '../lib/orchestrator-workflow-stack';

const app = new App();
new OrchestratorWorkflowStack(app, 'OrchestratorWorkflowStack');
