package com.cool.java_core;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStringBuilder {
    private StringBuilder sb = new StringBuilder();
    private final Deque<String> history = new ArrayDeque<>(20);

    public MyStringBuilder append(String str) {
        saveSnapshot(sb);
        sb.append(str);
        return this;
    }

    private void saveSnapshot(StringBuilder sb){
        if (history.size() >= 20){
            history.removeLast();
        }
        history.push(sb.toString());
    }

    public void undo() {
        if (!history.isEmpty()) {
            sb = new StringBuilder(history.pollFirst());
        }
    }

}
