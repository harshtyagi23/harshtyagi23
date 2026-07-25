"""Flask API with rate limiting example."""

from flask import Flask, request, jsonify
from functools import wraps
from rate_limiter import RateLimiter
import time

app = Flask(__name__)

# Per-client rate limiter: 100 token capacity, 10 tokens/sec refill rate
# This allows 10 requests/sec sustained or burst up to 100
limiter = RateLimiter(capacity=100, refill_rate=10.0)


def rate_limit(f):
    """Decorator to apply rate limiting to endpoints."""
    @wraps(f)
    def decorated_function(*args, **kwargs):
        # Extract client ID from X-Client-ID header or use IP
        client_id = request.headers.get("X-Client-ID", request.remote_addr)

        allowed, retry_after = limiter.allow_request(client_id, tokens_needed=1)

        if not allowed:
            # Return 429 Too Many Requests with Retry-After header
            response = jsonify({"error": "Rate limit exceeded", "retry_after": retry_after})
            response.status_code = 429
            response.headers["Retry-After"] = str(int(retry_after) + 1)
            return response

        return f(*args, **kwargs)

    return decorated_function


@app.route("/api/process", methods=["POST"])
@rate_limit
def process_request():
    """Process a request (rate limited)."""
    client_id = request.headers.get("X-Client-ID", request.remote_addr)
    data = request.get_json() or {}

    # Simulate processing
    time.sleep(0.1)

    return jsonify({
        "status": "success",
        "message": f"Processed: {data.get('message', 'no message')}",
        "client": client_id,
        "timestamp": time.time()
    })


@app.route("/health", methods=["GET"])
def health():
    """Health check (not rate limited)."""
    return jsonify({"status": "healthy"})


@app.route("/api/stats", methods=["GET"])
def stats():
    """Get rate limiter stats for a client."""
    client_id = request.headers.get("X-Client-ID", request.remote_addr)
    stats = limiter.get_stats(client_id)

    return jsonify({
        "client": client_id,
        "limiter_stats": stats or {"error": "No stats yet"}
    })


if __name__ == "__main__":
    # Run on port 5000 for local testing
    app.run(debug=True, port=5000)
