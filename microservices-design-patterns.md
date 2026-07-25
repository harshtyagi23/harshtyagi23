## Microservices Design Patterns (Reference List)

Learners should be able to *name the pattern they used and why* during the showcase.

### Decomposition Patterns
| Pattern | What it means | Where you'll use it |
|---|---|---|
| **Decompose by Business Capability** | One service per business function (Pricing, Trades, Accounts) | Both projects |
| **Decompose by Subdomain (DDD)** | Services map to bounded contexts | Both projects |
| **Strangler Fig** | Incrementally replace a monolith by routing traffic to new services | Discussion topic |
| **Sidecar** | Helper process deployed alongside the main service (logging, proxy) | ECS task with log router |

### Data Management Patterns
| Pattern | What it means | Where you'll use it |
|---|---|---|
| **Database per Service** | Each service owns its own datastore | DynamoDB (Pricing) vs RDS MySQL (Accounts) |
| **Saga (Orchestration & Choreography)** | Distributed transaction as a sequence of local transactions + compensations | Project 2 (Step Functions saga) |
| **CQRS** | Separate write model from read model | Project 1 (write to DynamoDB, query via API/OpenSearch) |
| **Event Sourcing** | Store state changes as an immutable event log | Discussion / stretch goal |
| **Transactional Outbox** | Write event + state in one transaction, publish asynchronously | Project 2 stretch |
| **Shared Data Anti-pattern awareness** | Why services must not share tables | Design review topic |

### Integration / Communication Patterns
| Pattern | What it means | Where you'll use it |
|---|---|---|
| **API Gateway pattern** | Single entry point that routes/aggregates | Amazon API Gateway in both |
| **Backends for Frontends (BFF)** | Separate API layer per client type | React dashboard (optional) |
| **Asynchronous Messaging** | Services communicate via queues/topics, not direct calls | SNS/SQS everywhere |
| **Publish–Subscribe** | One event, many independent consumers | SNS fan-out in Project 1 |
| **Request–Reply over Messaging** | Correlate async responses | Discussion |
| **Message Broker / Event Bus** | Central routing of events with rules | EventBridge |
| **Claim Check** | Put large payload in storage, pass a reference in the message | S3 key in SQS message (Project 1) |
| **Content-Based Routing** | Route messages by payload attributes | EventBridge rules / SNS filter policies |

### Reliability Patterns
| Pattern | What it means | Where you'll use it |
|---|---|---|
| **Circuit Breaker** | Stop calling a failing dependency | Lambda + DynamoDB flag / library |
| **Retry with Exponential Backoff + Jitter** | Safe retries of transient failures | SDK defaults, SQS redrive |
| **Dead Letter Queue (DLQ)** | Park messages that repeatedly fail | SQS DLQs in both projects |
| **Idempotent Consumer** | Processing the same message twice is safe | Conditional writes in DynamoDB |
| **Bulkhead** | Isolate resources so one failure doesn't sink everything | Separate queues/concurrency limits |
| **Timeouts & Fail Fast** | Bound every remote call | Lambda timeouts, SDK config |

### Observability & Deployment Patterns
| Pattern | What it means | Where you'll use it |
|---|---|---|
| **Log Aggregation** | Centralize structured logs | CloudWatch Logs |
| **Metrics & Alarms** | Emit business + system metrics | CloudWatch custom metrics |
| **Distributed Tracing** | Trace a request across services | Correlation IDs in logs (X-Ray optional) |
| **Health Check API** | `/health` endpoint per service | ECS/ALB target health |
| **Externalized Configuration** | Config outside code | SSM/Secrets Manager, env vars |
| **Service per Container / Serverless Function** | Deployment unit choices | ECS Fargate vs Lambda |

---