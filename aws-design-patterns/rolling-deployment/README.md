# Rolling Deployment

Rolling Deployment updates a service incrementally by replacing instances or tasks in batches instead of all at once. It keeps the service available while new versions are introduced gradually.

## Problem it solves

All-at-once deployments can cause full outages if a release is faulty. Rolling rollout limits impact by changing only part of the fleet at a time.

## When to use

- You need continuous availability during releases.
- You run homogeneous service replicas.
- You want lower temporary capacity cost than blue/green.

## Basic flow

1. Start with all instances on current version.
2. Replace a small batch with the new version.
3. Verify health and metrics.
4. Continue batch by batch until complete.

## Mermaid diagram

```mermaid
flowchart LR
    OldA[Old v1 Tasks] --> NewA[New v2 Batch 1]
    NewA --> NewB[New v2 Batch 2]
    NewB --> NewC[New v2 Batch 3]
```

## Example code

The example uses Python to simulate batched replacement and rollback decision points.

## Why it fits AWS well

- ECS and Auto Scaling support controlled batch replacement.
- ALB health checks gate batch progression.
- CloudWatch alarms help detect rollout regressions.

## Failure handling

- Stop rollout if error or latency alarms breach thresholds.
- Keep previous task set for quick stabilization.

## Security and observability

- Track deployment id, batch number, and version in logs.
- Restrict deployment roles with least privilege.

## Tradeoffs

- Slower than full cutover.
- Mixed-version period can complicate debugging.

## Try it locally

1. Run `pytest -q` in `example/`.
2. Run `npm install && npm test -- --runInBand` in `cdk/`.
