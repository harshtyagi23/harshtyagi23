# Event-Driven File Processing

Event-Driven File Processing reacts to file uploads automatically. In AWS, an object lands in S3 and triggers downstream processing without polling.

## Problem it solves

Use this pattern when file arrival should trigger processing immediately and asynchronously. It removes manual checks and lets uploads drive the workflow.

It is not ideal when consumers need strict global ordering across many objects or when every step must be synchronous to the uploader.

## When to use

- You process uploads such as images, CSVs, logs, or documents.
- You want processing to start as soon as files arrive.
- You want producers decoupled from processing workers.

## Basic flow

1. A producer uploads a file to S3.
2. S3 emits an `ObjectCreated` event.
3. Lambda receives the event and fetches object metadata/content.
4. Lambda runs processing logic for that object.

## What happens in AWS

- S3 event notifications trigger Lambda directly for matching object events.
- Lambda scales with file arrival rate.
- Each object event is processed independently, which fits bursty uploads.
- Retries can happen, so handlers should be idempotent.

## Message shape

S3 sends event records like:

```json
{
  "Records": [
    {
      "s3": {
        "bucket": { "name": "uploads-bucket" },
        "object": { "key": "incoming/report-123.csv" }
      }
    }
  ]
}
```

The object key is the main input to processing.

## Mermaid diagram

```mermaid
flowchart LR
	Producer[Uploader]
	Bucket[S3 Bucket]
	Consumer[Lambda Processor]

	Producer --> Bucket
	Bucket --> Consumer
```

## Example code

The `example/` folder uses Python because S3 event parsing and object processing are easiest to show in a compact handler.

The `cdk/` folder uses AWS CDK in TypeScript to provision S3, Lambda, and bucket notification wiring.

### Example snippets

```python
def upload_file(s3_client, bucket: str, key: str, content: str) -> None:
	s3_client.put_object(Bucket=bucket, Key=key, Body=content.encode("utf-8"))
```

```python
def handler(event, context):
	for record in event.get("Records", []):
		bucket = record["s3"]["bucket"]["name"]
		key = record["s3"]["object"]["key"]
		obj = s3_client.get_object(Bucket=bucket, Key=key)
		text = obj["Body"].read().decode("utf-8")
		print(f"processed file: {key}, bytes={len(text)}")
	return {"statusCode": 200, "body": "processed"}
```

### CDK snippet

```ts
const bucket = new s3.Bucket(this, 'UploadsBucket');

const processor = new lambda.Function(this, 'FileProcessor', {
	runtime: lambda.Runtime.PYTHON_3_12,
	handler: 'consumer.handler',
	code: lambda.Code.fromAsset('../example'),
});

bucket.grantRead(processor);
bucket.addEventNotification(s3.EventType.OBJECT_CREATED, new s3n.LambdaDestination(processor));
```

## Why it fits AWS well

- S3 is a natural durable landing zone for files.
- Native S3-to-Lambda notifications avoid custom schedulers.
- Lambda provides elastic, pay-per-use processing.

## Failure handling

- Handlers should be idempotent because retries can occur.
- Validate object existence and format before deep processing.
- Use DLQ or on-failure destinations if processing errors must be retained.
- Avoid deleting source objects before downstream completion.

## Security and observability

- Producer needs `s3:PutObject` permissions.
- Lambda needs `s3:GetObject` and CloudWatch logs permissions.
- Monitor Lambda errors, duration, and throttles.
- Log bucket/key so failures are traceable to the source object.

## Tradeoffs

- Event-driven execution can make step-by-step debugging harder.
- High upload spikes can cause concurrent Lambda bursts.
- Some workloads still need orchestration beyond single-object handlers.

## Try it locally

1. Run the Python tests in `example/`.
2. Run `npm test -- --runInBand` in `cdk/`.
3. Use `cdk synth` to inspect the generated infrastructure before deployment.
