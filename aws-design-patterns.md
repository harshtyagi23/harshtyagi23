## AWS Cloud / Integration Design Patterns

These are the AWS-flavored patterns mentors will expect you to recognize, grouped by implementation complexity.

### Standard README Structure

Use the same section order as the Fan-Out page for every pattern so the folders read consistently:

1. Problem it solves
2. When to use
3. Basic flow
4. Mermaid diagram
5. Example code
6. Why it fits AWS well
7. Failure handling
8. Security and observability
9. Tradeoffs
10. Try it locally

### Implementation Guidance

Before starting any remaining pattern, pause and choose the example language that best fits the pattern rather than forcing one default everywhere.

- Prefer Python in `example/` when it keeps the pattern easy to understand or keeps the sample small.
- Use Java snippets in `example/` when the pattern is more natural for Java apps or the use case is clearly Java-centric.
- Keep the folder layout consistent with Fan-Out: `README.md`, `example/`, `cdk/`, and tests that sit next to the code they validate.
- Make the CDK folder TypeScript-based unless there is a strong reason to do otherwise.

### Recommended Build Order

Build the remaining patterns in this order so each one reuses concepts introduced by the previous one:

1. Queue-Based Load Leveling - simplest SQS buffering pattern and a natural next step after Fan-Out.
2. DLQ + Redrive - adds failure recovery to queue-based patterns.
3. Work Queue / Competing Consumers - builds on SQS and consumer scaling.
4. Idempotency via Conditional Writes - needed for any queue- or event-driven consumer.
5. Claim Check - pairs naturally with queues and large payload handling.
6. Event-Driven File Processing - introduces S3 events and Lambda triggers.
7. Scheduled Batch (Cron in the cloud) - introduces EventBridge Scheduler and timed execution.
8. API Gateway + Lambda (Serverless API) - adds the request/response API shape.
9. Secrets & Config Externalization - useful across all app-backed patterns.
10. Cache-Aside - extends API and data-access patterns with performance optimization.
11. Pipes and Filters - shows stepwise event or message transformation.
12. Orchestrator (Workflow) Pattern - introduces Step Functions for control flow.
13. Event Bus Choreography - moves from orchestration to decentralized events.
14. Scatter-Gather - reuses fan-out ideas but focuses on aggregation.
15. Backend for Frontend (BFF) / API Composition - builds on aggregation for client-specific APIs.
16. CQRS Read Side / Search Offloading - separates write and read concerns.
17. Streaming Ingestion - introduces continuous stream processing with Kinesis.
18. ETL / Data Lake pattern - moves into batch analytics and data shaping.
19. Private Networking for Data Tier - adds network isolation for data-heavy apps.
20. Sidecar - introduces cross-cutting runtime concerns in ECS / EKS.
21. Transactional Outbox - adds reliable event publication after data writes.
22. Change Data Capture (CDC) / Data Replication - extends outbox/replication thinking to data movement.
23. Circuit Breaker - adds resilience at the client or service boundary.
24. Retry with Backoff / Jitter - pairs with circuit breaking and transient failure handling.
25. Timeout - makes latency and blast radius explicit.
26. Bulkhead - isolates workloads and prevents resource contention.
27. Blue/Green Deployment - starts the deployment and release engineering group.
28. Canary Deployment - refines rollout strategy with weighted traffic.
29. Rolling Deployment - covers the simpler operational rollout model.
30. Rate Limiting / Throttling - protects downstream dependencies and public APIs.
31. Multi-Region Failover - finishes with the highest-resilience topology.

If you want the learning curve to stay even smoother, keep each new pattern aligned to this same template: overview, diagram, code, AWS fit, tradeoffs, then tests.

### Easy

| Pattern | AWS Implementation | Typical Use |
|---|---|---|
| **Fan-Out** | S3 event or app event -> **SNS topic -> multiple SQS queues**, each with its own consumer | Broadcast the same event to multiple downstream consumers |
| **Queue-Based Load Leveling** | SQS between producer and consumer smooths spikes | Buffer bursts and protect slow consumers |
| **Event-Driven File Processing** | S3 `ObjectCreated` -> EventBridge / Lambda | Trigger processing when a file lands |
| **Scheduled Batch (Cron in the cloud)** | EventBridge Scheduler -> Lambda / Step Functions / AWS Batch | Run jobs on a schedule |
| **API Gateway + Lambda (Serverless API)** | REST API -> Lambda -> DynamoDB / RDS | Build lightweight APIs |
| **Claim Check** | Message carries S3 object key, not the payload | Keep large payloads out of queues |
| **DLQ + Redrive** | SQS redrive policy, `maxReceiveCount` | Capture failed messages and retry later |
| **Idempotency via Conditional Writes** | DynamoDB `attribute_not_exists` / version numbers | Prevent duplicate processing |
| **Secrets & Config Externalization** | Secrets Manager + KMS | Keep credentials and config out of code |
| **Pipes and Filters** | Chain of Lambdas each doing one transform | Apply a sequence of small processing steps |
| **Cache-Aside** | Lambda in-memory cache / DynamoDB DAX / ElastiCache | Read through cache before the database |
| **Work Queue / Competing Consumers** | Multiple workers pull from SQS | Scale out background processing safely |

### Medium

| Pattern | AWS Implementation | Typical Use |
|---|---|---|
| **Orchestrator (Workflow) Pattern** | **Step Functions** state machine coordinating Lambdas with retries / catch | Coordinate multi-step work |
| **Event Bus Choreography** | EventBridge custom bus + rules, services react to events | Decentralized event-driven workflows |
| **Streaming Ingestion** | Kinesis Data Streams -> Lambda consumer | Process continuous event streams |
| **ETL / Data Lake pattern** | S3 raw -> **Glue** job -> S3 curated (Parquet) | Transform raw data into analytics-ready data |
| **CQRS Read Side / Search Offloading** | Write DB + **OpenSearch** for queries | Separate write and query workloads |
| **Private Networking for Data Tier** | RDS in private subnets, Lambda in VPC, **VPC Endpoints** for S3 / Secrets | Keep data plane private |
| **Scatter-Gather** | Fan out requests, aggregate responses (Step Functions `Map` / `Parallel`) | Parallelize work and combine results |
| **Strangler / Facade at the Edge** | Route 53 + ALB path routing to new services | Gradually replace a legacy system |
| **Backend for Frontend (BFF) / API Composition** | API Gateway + Lambda aggregation layer | Shape backend data for a client app |

### Complex

| Pattern | AWS Implementation | Typical Use |
|---|---|---|
| **Saga with Compensation** | Step Functions with compensating steps on failure | Keep distributed transactions consistent |
| **Sidecar** | ECS / EKS task or pod with Envoy, log shipper, auth proxy, or metrics agent | Add cross-cutting capabilities beside the app |
| **Transactional Outbox** | App writes business data + outbox row, then publishes via CDC | Guarantee event publication after DB write |
| **Change Data Capture (CDC) / Data Replication** | DB binlog / WAL -> **Debezium** or AWS DMS -> stream / queue -> target region DB | Replicate data to another region or service |
| **Circuit Breaker** | SDK retries with open / half-open breaker behavior | Stop cascading failures |
| **Retry with Backoff / Jitter** | Exponential backoff in Lambda / SDK / Step Functions | Recover from transient failures safely |
| **Timeout** | Lambda timeout, SDK timeouts, Step Functions heartbeat / timeout | Bound latency and failure blast radius |
| **Bulkhead** | Separate SQS queues, Lambda concurrency, ECS task sets | Isolate workloads from each other |
| **Blue/Green Deployment** | CodeDeploy with Lambda aliases or ALB target groups | Safer release cutover |
| **Canary Deployment** | Weighted Lambda alias or traffic shifting via CodeDeploy | Gradual production rollout |
| **Rolling Deployment** | ECS service deployment configuration / ASG replacement | Update instances in batches |
| **Rate Limiting / Throttling** | API Gateway usage plans, WAF rules, service quotas | Protect downstream dependencies |
| **Multi-Region Failover** | Route 53 health checks, DynamoDB global tables, cross-region replication | Keep the system available during region loss |

---

### Overlap Notes

- CQRS Read Side and Search Offloading are intentionally grouped together because they usually describe the same read-optimization pattern.
- Transactional Outbox and CDC are adjacent but not identical: outbox is the write-side reliability pattern, while CDC is the transport mechanism.
- BFF and API Composition are the same design idea in different naming conventions.
