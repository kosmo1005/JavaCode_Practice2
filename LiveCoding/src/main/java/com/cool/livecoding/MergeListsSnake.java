package com.cool.livecoding;

// Ограничения:
// - Элементы должны добавляться поочерёдно: 1 элемент из первого списка, затем 1 элемент из второго.
// - Необходимо корректно обработать ситуацию, когда списки имеют разную длину.
// - Метод должен вернуть новый результирующий список.
// Примеры:
//
// input:
// first = [1, 2, 3]
// second = [4, 5, 6]
// output:
// [1, 4, 2, 5, 3, 6]
//
// input:
// first = [1, 2, 3, 4]
// second = [5, 6]
// output:
// [1, 5, 2, 6, 3, 4]
//
// input:
// first = [1]
// second = [2, 3, 4]
// output:
// [1, 2, 3, 4]

import java.util.ArrayList;
import java.util.List;

public class MergeListsSnake {

    public static void main(String[] args) {
        List<Integer> first = List.of(1, 2, 3, 4);
        List<Integer> second = List.of(5, 6);
        System.out.println(mergeSnake(first, second));
    }

    public static List<Integer> mergeSnake(List<Integer> first, List<Integer> second) {
        int maxSize = Math.max(first.size(), second.size());
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            if (i < first.size() && i < second.size()){
                result.add(first.get(i));
                result.add(second.get(i));
            }

            if (i >= first.size()){
                result.add(second.get(i));
            }
            if (i >= second.size()) {
                result.add(first.get(i));
            }
        }
        return result;
    }
}