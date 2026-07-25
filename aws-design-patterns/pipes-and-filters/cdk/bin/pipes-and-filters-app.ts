#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { PipesAndFiltersStack } from '../lib/pipes-and-filters-stack';

const app = new App();
new PipesAndFiltersStack(app, 'PipesAndFiltersStack');