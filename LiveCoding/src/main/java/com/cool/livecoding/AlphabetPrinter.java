package com.cool.livecoding;

public class AlphabetPrinter {
    public static void main(String[] args) {
        printAlphabet();

    }

    public static void printAlphabet() {
        char ch = 65;
        while(ch <= 90){
            System.out.print(ch);
            ch++;
        }
    }
}
