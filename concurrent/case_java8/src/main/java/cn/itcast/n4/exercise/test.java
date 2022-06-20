package cn.itcast.n4.exercise;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] ints = sort1();
        for (int i = 0; i <ints.length ; i++) {
            System.out.println(ints[i]);
        }

    }
    public static int[] sort1() {

        int[] nums = {3, 2, 1, 5, 6, 4};
        Arrays.sort(nums);
//        if (nums.length <= 1) {
//            return nums[0];
//        }
        int temp;
        for (int i = nums.length - 1; i >= 0; i--) {
            int left = 0, right = 1;
            while (right <= i){
                if (nums[left] > nums[right]) {
                    temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;
                }
                left++;
                right++;
            }
        }
        return nums;
    }
}
