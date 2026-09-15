package com.cool.livecoding;

// Ограничения:
// - Необходимо найти первый элемент, который встречается только один раз.
// - Порядок элементов должен сохраняться.
// - Если уникальный элемент отсутствует — вернуть null.
//
// Примеры:
//
// input:
// [4, 5, 1, 2, 0, 4, 1, 2]
// output:
// 5
//
// input:
// [7, 7, 3, 3, 9]
// output:
// 9
//
// input:
// [1, 1, 2, 2, 3, 3]
// output:
// null

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstUniqueElement {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(4, 5, 1, 2, 0, 4, 1, 2);
        System.out.println(findFirstUnique(numbers));

    }

    public static Integer findFirstUnique(List<Integer> numbers) {
        Map<Integer,Integer> numbersMap = new LinkedHashMap<>();
        for(Integer n : numbers){
            if(!numbersMap.containsKey(n)){
                numbersMap.put(n,1);
            } else {
                int count = numbersMap.get(n);
                numbersMap.put(n, count+1);
                // idea подсказывает .compute()
                // я знал, что есть метод мапы в одну строчку, но не помнил какой
            }
        }
        return numbersMap.entrySet().stream()
                .filter(x -> x.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
