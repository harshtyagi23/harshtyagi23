import * as cdk from "aws-cdk-lib";
import * as apigateway from "aws-cdk-lib/aws-apigateway";
import * as lambda from "aws-cdk-lib/aws-lambda";
import * as iam from "aws-cdk-lib/aws-iam";
import * as cloudwatch from "aws-cdk-lib/aws-cloudwatch";
import { Construct } from "constructs";

export class RateLimitingStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // Lambda execution role
    const lambdaRole = new iam.Role(this, "LambdaExecutionRole", {
      assumedBy: new iam.ServicePrincipal("lambda.amazonaws.com"),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName(
          "service-role/AWSLambdaBasicExecutionRole"
        ),
      ],
    });

    // Lambda function to process requests
    const processFunction = new lambda.Function(this, "ProcessFunction", {
      runtime: lambda.Runtime.PYTHON_3_11,
      handler: "index.handler",
      role: lambdaRole,
      code: lambda.Code.fromInline(`
import json
import time

def handler(event, context):
    # Simulate processing
    time.sleep(0.1)
    
    return {
        "statusCode": 200,
        "body": json.dumps({
            "status": "success",
            "message": "Request processed",
            "timestamp": time.time()
        })
    }
`),
      timeout: cdk.Duration.seconds(30),
    });

    // Lambda function to get stats
    const statsFunction = new lambda.Function(this, "StatsFunction", {
      runtime: lambda.Runtime.PYTHON_3_11,
      handler: "index.handler",
      role: lambdaRole,
      code: lambda.Code.fromInline(`
import json

def handler(event, context):
    return {
        "statusCode": 200,
        "body": json.dumps({
            "limiter_stats": {
                "capacity": 100,
                "refill_rate": 10.0
            }
        })
    }
`),
      timeout: cdk.Duration.seconds(10),
    });

    // API Gateway REST API with throttling
    const api = new apigateway.RestApi(this, "RateLimitingAPI", {
      restApiName: "Rate Limiting API",
      description: "API with throttling and rate limiting",
      defaultCorsPreflightOptions: {
        allowOrigins: apigateway.Cors.ALL_ORIGINS,
        allowMethods: apigateway.Cors.ALL_METHODS,
      },
    });

    // /api resource
    const apiResource = api.root.addResource("api");

    // /api/process resource and method
    const processResource = apiResource.addResource("process");

    const processMethod = processResource.addMethod(
      "POST",
      new apigateway.LambdaIntegration(processFunction, {
        requestTemplates: {
          "application/json": '{"body": $input.json("$")}',
        },
        integrationResponses: [
          {
            statusCode: "200",
            responseTemplates: {
              "application/json": "$input.json('$')",
            },
          },
        ],
      }),
      {
        methodResponses: [{ statusCode: "200" }],
      }
    );

    // Apply usage plan with throttling per API key
    const usagePlan = api.addUsagePlan("UsagePlan", {
      name: "Basic Usage Plan",
      description: "Rate limited usage plan",
      throttle: {
        rateLimit: 100,    // 100 requests per second per API key
        burstLimit: 200,   // 200 concurrent requests per API key
      },
      quota: {
        limit: 1000000,    // 1M requests per day
        period: apigateway.Period.DAY,
      },
    });

    // Create API key
    const apiKey = api.addApiKey("ApiKey", {
      description: "API Key for rate limiting example",
    });

    // Associate API key with usage plan and stage
    usagePlan.addApiKey(apiKey);
    usagePlan.addApiStage({
      stage: api.deploymentStage,
    });

    // /api/stats resource and method (no rate limit quota)
    const statsResource = apiResource.addResource("stats");

    statsResource.addMethod(
      "GET",
      new apigateway.LambdaIntegration(statsFunction, {
        integrationResponses: [
          {
            statusCode: "200",
            responseTemplates: {
              "application/json": "$input.json('$')",
            },
          },
        ],
      }),
      {
        methodResponses: [{ statusCode: "200" }],
      }
    );

    // CloudWatch Alarms for monitoring
    const throttlingAlarm = new cloudwatch.Alarm(
      this,
      "ThrottlingAlarm",
      {
        metric: new cloudwatch.Metric({
          namespace: "AWS/ApiGateway",
          metricName: "Count",
          statistic: "Sum",
          period: cdk.Duration.minutes(1),
          dimensionsMap: {
            ApiName: api.restApiName,
            Stage: api.deploymentStage.stageName,
          },
        }),
        threshold: 100,
        evaluationPeriods: 1,
        alarmDescription:
          "Alert when API Gateway receives many requests",
        alarmName: "RateLimitingThrottlingAlarm",
      }
    );

    const lambda4xxAlarm = new cloudwatch.Alarm(this, "Lambda4xxAlarm", {
      metric: processFunction.metricErrors({
        statistic: "Sum",
        period: cdk.Duration.minutes(1),
      }),
      threshold: 5,
      evaluationPeriods: 1,
      alarmDescription: "Alert when Lambda returns errors",
      alarmName: "RateLimitingLambda4xxAlarm",
    });

    // Outputs
    new cdk.CfnOutput(this, "ApiEndpoint", {
      value: api.url,
      description: "API Gateway endpoint URL",
    });

    new cdk.CfnOutput(this, "ApiKeyId", {
      value: apiKey.keyId,
      description: "API Key ID for testing",
    });

    new cdk.CfnOutput(this, "ApiStageName", {
      value: api.deploymentStage.stageName,
      description: "API Gateway stage name",
    });
  }
}
