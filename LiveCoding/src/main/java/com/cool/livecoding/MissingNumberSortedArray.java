package com.cool.livecoding;
// Ограничения:
// - Дан отсортированный массив целых неотрицательных чисел.
// - В массиве допускаются дубликаты.
// - Необходимо найти минимальное пропущенное число в последовательности.
// - Возвращаемое значение — первое "разрывное" число.
//
// Примеры:
//
// input:
// [0, 1, 2, 4, 5]
// output:
// 3
//
// input:
// [1, 2, 3, 4]
// output:
// 0
//
// input:
// [0, 0, 1, 1, 2, 2, 4]
// output:
// 3


public class MissingNumberSortedArray {

    public static void main(String[] args) {

        int[] arr = {0, 0, 1, 1, 2, 2, 4};
        System.out.println(findMissing(arr));
    }

    public static int findMissing(int[] arr) {
        for(int i = 0; i < arr.length; i++){
            if(i == 0 && arr[i] != 0){
                return 0;
            }
            if (arr[i + 1] != arr[i]+1 && arr[i+1] != arr[i]) {
                return arr[i]+1;
            }
        }

        return -1;
    }
}
