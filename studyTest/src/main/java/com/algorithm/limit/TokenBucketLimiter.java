package com.algorithm.limit;

public class TokenBucketLimiter {

    private long capacity;
    private long windowTimeInSeconds;
    long lastRefillTimeStamp;
    long refillCountPerSecond;
    long availableTokens;

    public TokenBucketLimiter(long capacity, long windowTimeInSeconds) {
        this.capacity = capacity;
        this.windowTimeInSeconds = windowTimeInSeconds;
        lastRefillTimeStamp = System.currentTimeMillis();
        refillCountPerSecond = capacity / windowTimeInSeconds;
        availableTokens = 0;
    }

    public long getAvailableTokens() {
        return this.availableTokens;
    }

    public boolean tryAcquire() {

        //更新令牌桶
        refill();

        if (availableTokens > 0) {
            --availableTokens;
            return true;
        } else {
            return false;
        }
    }


    private void refill() {
        long now = System.currentTimeMillis();

        if (now > lastRefillTimeStamp) {

            long elapsedTime = now - lastRefillTimeStamp;

            int tokensToBeAdded = (int) ((elapsedTime / 1000) * refillCountPerSecond);

            if (tokensToBeAdded > 0) {
                availableTokens = Math.min(capacity, availableTokens + tokensToBeAdded);
                lastRefillTimeStamp = now;
            }
        }
    }

}