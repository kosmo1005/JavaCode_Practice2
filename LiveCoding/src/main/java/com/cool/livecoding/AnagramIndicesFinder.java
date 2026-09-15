package com.cool.livecoding;

// Ограничения:
// - Необходимо найти все начальные индексы подстрок в text, которые являются анаграммами слова word.
// - Анаграмма — это перестановка символов слова.
// - Подстрока должна иметь длину, равную длине word.
// - Результат возвращается как список индексов.
//
// Примеры:
//
// input:
// text = "cbaebabacd", word = "abc"
// output:
// [0, 6]
//
// input:
// text = "abab", word = "ab"
// output:
// [0, 1, 2]
//
// input:
// text = "afdgzyxksldfm", word = "xyz"
// output:
// [3]

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnagramIndicesFinder {

    public static void main(String[] args) {

        String text = "abab";
        String word = "ab";
        System.out.println(findAnagrams(text,word));

    }

    public static List<Integer> findAnagrams(String text, String word) {
        List<Integer> result = new ArrayList<>();
        char[] textArr = text.toCharArray();
        char[] wordArr = word.toCharArray();
        Arrays.sort(wordArr);
        int pointer = word.length() - 1;

        while (pointer < textArr.length){
            char[] subString = new char[word.length()];
            for(int i = 0; i < word.length(); i++){
                subString[i] = textArr[pointer - i];
            }
            Arrays.sort(subString);
            if(Arrays.equals(wordArr, subString)){
                result.add(pointer - (word.length()-1));
            }
            pointer++;

        }

        return result;
    }
}
