package com.cool.java_core;

import java.util.Stack;

public class MyStringBuilder  {
    private StringBuilder sb = new StringBuilder();
    private final Stack <String> history = new Stack<>();

    public MyStringBuilder append(String str) {
        saveSnapshot(sb);
        sb.append(str);
        return this;
    }

    private void saveSnapshot(StringBuilder sb){
        history.push(sb.toString());
    }

    public void undo() {
        if (!history.isEmpty()) {
            sb = new StringBuilder(history.pop());
        }
    }

}
