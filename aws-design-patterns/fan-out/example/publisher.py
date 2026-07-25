from __future__ import annotations

import json
from typing import Any


def publish_event(sns_client: Any, topic_arn: str, order_id: str) -> None:
    payload = json.dumps({"orderId": order_id, "eventType": "OrderCreated"})
    sns_client.publish(
        TopicArn=topic_arn,
        Message=payload,
        Subject="OrderCreated",
    )


if __name__ == "__main__":
    print("Use publish_event(...) from a script or Lambda helper.")
