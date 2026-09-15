package com.cool.livecoding;

// Ограничения:
// - Необходимо вывести числа, которые встречаются только в одном из двух массивов.
// - Повторы внутри массива не должны влиять на результат (важен факт наличия элемента).
// - Результат может быть в любом порядке.
//
// Примеры:
//
// input:
// arr1 = [1, 2, 3, 4]
// arr2 = [3, 4, 5, 6]
// output:
// [1, 2, 5, 6]
//
// input:
// arr1 = [1, 1, 2]
// arr2 = [2, 3, 3]
// output:
// [1, 3]
//
// input:
// arr1 = [7, 8]
// arr2 = [7, 8]
// output:
// []

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SymmetricDifferenceArrays {

    public static void main(String[] args) {
        int[] arr1 = {7,8};
        int[] arr2 = {7,8};
        System.out.println(findOnlyInOne(arr1,arr2));

    }

    public static List<Integer> findOnlyInOne(int[] arr1, int[] arr2) {

        int maxLength = Math.max(arr1.length, arr2.length);
        List<Integer> result = new ArrayList<>();
        Set<Integer> uniqNumbersFromArr1 = new HashSet<>();
        Set<Integer> uniqNumbersFromArr2 = new HashSet<>();
        for (int i = 0; i < maxLength; i++) {
            if (i < arr1.length && i < arr2.length){
                uniqNumbersFromArr1.add(arr1[i]);
                uniqNumbersFromArr2.add(arr2[i]);
            }
            if (i >= arr1.length){
                uniqNumbersFromArr2.add(arr2[i]);
            }
            if (i >= arr2.length) {
                uniqNumbersFromArr1.add(arr1[i]);
            }
        }
        for(Integer n : uniqNumbersFromArr1){
            if(!uniqNumbersFromArr2.contains(n)){
                result.add(n);
            }
        }
        for(Integer n : uniqNumbersFromArr2){
            if(!uniqNumbersFromArr1.contains(n)){
                result.add(n);
            }
        }
        return result;
    }
}