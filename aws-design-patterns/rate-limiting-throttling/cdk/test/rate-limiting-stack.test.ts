import * as cdk from "aws-cdk-lib";
import * as assertions from "aws-cdk-lib/assertions";
import { RateLimitingStack } from "../lib/rate-limiting-stack";

describe("RateLimitingStack", () => {
  let app: cdk.App;
  let stack: RateLimitingStack;

  beforeEach(() => {
    app = new cdk.App();
    stack = new RateLimitingStack(app, "TestStack");
  });

  it("creates an API Gateway REST API", () => {
    assertions.Template.fromStack(stack).hasResourceProperties(
      "AWS::ApiGateway::RestApi",
      {
        Name: "Rate Limiting API",
      }
    );
  });

  it("creates Lambda functions", () => {
    assertions.Template.fromStack(stack).resourceCountIs(
      "AWS::Lambda::Function",
      2 // ProcessFunction and StatsFunction
    );
  });

  it("creates API Gateway Usage Plan with throttling", () => {
    assertions.Template.fromStack(stack).hasResourceProperties(
      "AWS::ApiGateway::UsagePlan",
      {
        Throttle: {
          RateLimit: 100,
          BurstLimit: 200,
        },
      }
    );
  });

  it("creates API key", () => {
    assertions.Template.fromStack(stack).resourceCountIs(
      "AWS::ApiGateway::ApiKey",
      1
    );
  });

  it("creates CloudWatch alarms for monitoring", () => {
    assertions.Template.fromStack(stack).resourceCountIs(
      "AWS::CloudWatch::Alarm",
      2 // ThrottlingAlarm and Lambda4xxAlarm
    );
  });

  it("creates alarm for API Gateway throttling", () => {
    assertions.Template.fromStack(stack).resourceCountIs(
      "AWS::CloudWatch::Alarm",
      2
    );
  });

  it("creates alarm for Lambda errors", () => {
    assertions.Template.fromStack(stack).hasResourceProperties(
      "AWS::CloudWatch::Alarm",
      {
        AlarmName: "RateLimitingLambda4xxAlarm",
        Threshold: 5,
      }
    );
  });

  it("has outputs for API endpoint and API key", () => {
    const template = assertions.Template.fromStack(stack);
    template.hasOutput("ApiEndpoint", {});
    template.hasOutput("ApiKeyId", {});
    template.hasOutput("ApiStageName", {});
  });
});
