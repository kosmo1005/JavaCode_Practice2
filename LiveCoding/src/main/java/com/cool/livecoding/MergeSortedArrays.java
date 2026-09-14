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

        int[] arr1 = {5, 10, 15, 15};
        int[] arr2 = {1, 2, 15, 20};
        System.out.println(Arrays.toString(merge(arr1, arr2)));

    }

    public static int[] merge(int[] arr1, int[] arr2) {

        int[] result = new int[arr1.length + arr2.length];
        int resultIndex = 0;
        int i = 0;
        int j = 0;

        while (i < arr1.length && j < arr2.length){
            if(arr1[i] < arr2[j]){
                result[resultIndex] = arr1[i];
                resultIndex++;
                i++;
                while (i < arr1.length && arr1[i] < arr2[j]){
                    result[resultIndex] = arr1[i];
                    resultIndex++;
                    i++;
                }
            } else {
                result[resultIndex] = arr2[j];
                resultIndex++;
                j++;
                while (j < arr2.length && arr2[j] < arr1[i]){
                    result[resultIndex] = arr2[j];
                    resultIndex++;
                    j++;
                }
            }
        }
        while (i < arr1.length){
            result[resultIndex] = arr1[i];
            resultIndex++;
            i++;
        }
        while (j < arr2.length){
            result[resultIndex] = arr2[j];
            resultIndex++;
            j++;
        }

        return result;
    }
}