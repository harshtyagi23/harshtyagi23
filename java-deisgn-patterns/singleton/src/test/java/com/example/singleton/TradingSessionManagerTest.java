package com.example.singleton;

import com.example.singleton.api.TradingSessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class TradingSessionManagerTest {
    @Test
    void returnsSameInstanceAcrossCalls() {
        TradingSessionManager first = TradingSessionManager.getInstance();
        TradingSessionManager second = TradingSessionManager.getInstance();

        assertSame(first, second);
    }

    @Test
    void sharesSessionStateAcrossReferences() {
        TradingSessionManager manager = TradingSessionManager.getInstance();
        manager.setCurrentSessionId("SESSION-777");

        TradingSessionManager anotherReference = TradingSessionManager.getInstance();

        assertEquals("SESSION-777", anotherReference.getCurrentSessionId());
    }
}
