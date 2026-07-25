#!/usr/bin/env node
import * as cdk from "aws-cdk-lib";
import { RateLimitingStack } from "../lib/rate-limiting-stack";

const app = new cdk.App();

new RateLimitingStack(app, "RateLimitingStack", {
  env: {
    account: process.env.CDK_DEFAULT_ACCOUNT,
    region: process.env.CDK_DEFAULT_REGION || "us-east-1",
  },
  description: "Rate Limiting & Throttling Pattern Stack",
});
