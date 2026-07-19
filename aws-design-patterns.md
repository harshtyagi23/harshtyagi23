## AWS Cloud / Integration Design Patterns

These are the AWS-flavored patterns mentors will expect you to recognize, grouped by implementation complexity.

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
