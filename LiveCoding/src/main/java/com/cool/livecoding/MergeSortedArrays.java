package com.cool.livecoding;

// Ограничения:
// - Даны два отсортированных массива.
// - Необходимо объединить их в один отсортированный массив.
// - Результат должен сохранять порядок возрастания.
// - Использовать дополнительный массив для результата допустимо.
//
// Примеры:
//
// input:
// arr1 = [1, 3, 5]
// arr2 = [2, 4, 6]
// output:
// [1, 2, 3, 4, 5, 6]
//
// input:
// arr1 = [1, 2, 2]
// arr2 = [2, 3, 4]
// output:
// [1, 2, 2, 2, 3, 4]
//
// input:
// arr1 = [5, 10, 15]
// arr2 = [1, 2, 20]
// output:
// [1, 2, 5, 10, 15, 20]


import java.util.Arrays;

public class MergeSortedArrays {

    public static void main(String[] args) {

        int[] arr1 = {5, 10, 15};
        int[] arr2 = {1, 2, 20};
        System.out.println(Arrays.toString(merge(arr1, arr2)));

    }

    public static int[] merge(int[] arr1, int[] arr2) {

        int[] result = new int[arr1.length + arr2.length];
        int maxLength = Math.max(arr1.length, arr2.length);
        int resultIndex = 0;

        for(int i = 0; i < maxLength; i++){
            if (i < arr1.length && i < arr2.length){
                result[resultIndex] = arr1[i];
                resultIndex++;
                result[resultIndex] = arr2[i];
                resultIndex++;
            }
            if (i >= arr1.length){
                result[resultIndex] = arr2[i];
                resultIndex++;
            }
            if (i >= arr2.length) {
                result[resultIndex] = arr1[i];
                resultIndex++;
            }
        }
        Arrays.sort(result);

        return result;
    }
}