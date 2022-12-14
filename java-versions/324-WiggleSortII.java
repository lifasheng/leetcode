/*
Medium
Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

You may assume the input array always has a valid answer.

Example 1:
Input: nums = [1,5,1,1,6,4]
Output: [1,6,1,5,1,4]
Explanation: [1,4,1,5,1,6] is also accepted.

Example 2:
Input: nums = [1,3,2,2,3,1]
Output: [2,3,1,3,1,2]
 
Constraints:
1 <= nums.length <= 5 * 104
0 <= nums[i] <= 5000
It is guaranteed that there will be an answer for the given input nums.
 
Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?
*/

class Solution {
    public void wiggleSort(int[] nums) {
        wiggleSort_usingSort(nums);
    }

    /*
    https://www.cnblogs.com/grandyang/p/5139057.html
    这道题给了我们一个无序数组，让我们排序成摆动数组，满足nums[0] < nums[1] > nums[2] < nums[3]...，并给了我们例子。
    我们可以先给数组排序，然后在做调整。调整的方法是找到数组的中间的数，相当于把有序数组从中间分成两部分，
    然后从前半段的末尾取一个，在从后半的末尾去一个，这样保证了第一个数小于第二个数，然后从前半段取倒数第二个，从后半段取倒数第二个，
    这保证了第二个数大于第三个数，且第三个数小于第四个数，以此类推直至都取完.

    我自己的想法也是先排序，但是我当时想的是从左右两部分从头到尾取数，这样大部分情况是可以的，但是有些情况不行。
    比如 【4 5 5 6】，对【4，5】和【5，6】两部分数组从头到尾取数结果还是【4，5，5，6】，这样就不行。
    但是按照上面的方法，对【4，5】和【5，6】两部分数组从尾到头取数就可以，【5， 6， 4， 5】

    写代码时需要注意
    1. 需要一个辅助数组。
    2. n为奇数的处理，很有技巧。 mid = (n + 1) / 2

    */
    public void wiggleSort_usingSort(int[] nums) {
        int n = nums.length;
        int[] clonedNums = Arrays.copyOf(nums, n);
        Arrays.sort(clonedNums);

        int k = (n + 1) / 2; // left part
        int j = n;  // right part
        for (int i = 0; i < n; ++i) {
            if (i % 2 == 0) {
                nums[i] = clonedNums[-- k];
            } else {
                nums[i] = clonedNums[-- j];
            }
        }
    }
    
    // TODO, how to solve it using O(N) way.
}

