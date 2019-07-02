package uber;

import java.util.ArrayList;
import java.util.List;

public class DesignRateLimiter {
    int tokenPerSecond;
    long lastCheckTime;
    int totalTokens;
    int capacity;

    // capacity is the burst
    DesignRateLimiter(int tokens, int seconds, int capacity) {
        tokenPerSecond = tokens /seconds; // refill rate
        lastCheckTime = System.currentTimeMillis();
        totalTokens = capacity;
    }

    public boolean acquire() {
        long nowTime = System.currentTimeMillis();
        long timePassed = (nowTime - lastCheckTime)/ 1000;
        lastCheckTime = nowTime;
        totalTokens += timePassed * tokenPerSecond;
        if (totalTokens > capacity) totalTokens = capacity;
        // throttle
        if (totalTokens < 1) return false;
        totalTokens--;
        return true;

    }
}
