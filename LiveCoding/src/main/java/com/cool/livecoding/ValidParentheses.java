package com.cool.livecoding;

// Ограничения:
// - Дана строка, содержащая только символы: '(', ')', '{', '}', '[' и ']'.
// - Необходимо проверить, является ли последовательность скобок корректной.
// - Последовательность корректна, если каждая открывающая скобка закрывается той же парной скобкой в правильном порядке.
// - Пустая строка считается корректной.
//
// Примеры:
//
// input:
// "()[]{}"
// output:
// true
//
// input:
// "([)]"
// output:
// false
//
// input:
// "{[()()]}"
// output:
// true

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {

    public static void main(String[] args) {

        String s = "()[]{}";
        System.out.println(isValid(s));

    }

    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Deque<Character> stack = new ArrayDeque<>();

        for(Character ch : chars){
            if ("({[".indexOf(ch) >= 0) {
                stack.push(ch);
            } else if (")}]".indexOf(ch) >= 0) {
                if (stack.isEmpty()) {
                    return false;
                }
                if(!stack.pop().equals(reverseChar(ch))){
                    return false;
                }
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }
    public static char reverseChar (char in){
        char out = 0;
        switch (in){
            case ')' -> {
                return '(';
            }
            case '}' -> {
                return '{';
            }
            case ']' -> {
                return '[';
            }
        }
        return out;
    }
}
