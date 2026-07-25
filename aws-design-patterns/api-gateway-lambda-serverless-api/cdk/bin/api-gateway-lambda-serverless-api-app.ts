#!/usr/bin/env node
import { App } from 'aws-cdk-lib';
import { ApiGatewayLambdaServerlessApiStack } from '../lib/api-gateway-lambda-serverless-api-stack';

const app = new App();
new ApiGatewayLambdaServerlessApiStack(app, 'ApiGatewayLambdaServerlessApiStack');