package com.cool.livecoding;

// Ограничения:
// - Необходимо найти индексы двух элементов массива, сумма которых равна target.
// - Гарантируется, что решение существует и оно единственное.
// - Один элемент нельзя использовать дважды.
//
// Примеры:
//
// input:
// nums = [2, 7, 11, 15], target = 9
// output:
// [0, 1]
//
// input:
// nums = [3, 2, 4], target = 6
// output:
// [1, 2]

import java.util.Arrays;

public class TwoSum {

    public static void main(String[] args) {

        int[] nums = {2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSum(nums, 9)));

    }

    public static int[] twoSum(int[] nums, int target) {

        int first = 0;
        int second = nums.length - 1;
        while (first < second){
            if(nums[first]+nums[second] == target){
                return new int[]{first,second};
            }
            if(nums[first]+nums[second] < target){
                first++;
            } else {
                second--;
            }
        }

        return null;
    }
}