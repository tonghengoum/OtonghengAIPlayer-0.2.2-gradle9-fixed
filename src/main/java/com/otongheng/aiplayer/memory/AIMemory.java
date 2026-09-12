package com.otongheng.aiplayer.memory;

import java.util.ArrayDeque;
import java.util.Deque;

public final class AIMemory {
    private final Deque<String> events = new ArrayDeque<>();
    public void remember(String event) {
        if (event == null || event.isBlank()) return;
        events.addFirst(event);
        while (events.size() > 40) events.removeLast();
    }
    public String latest() { return events.peekFirst() == null ? "none" : events.peekFirst(); }
    public int size() { return events.size(); }
}
