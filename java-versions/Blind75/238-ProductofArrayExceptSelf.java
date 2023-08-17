/*
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
You must write an algorithm that runs in O(n) time and without using the division operation.


Example 1:
Input: nums = [1,2,3,4]
Output: [24,12,8,6]

Example 2:
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
 
Constraints:
2 <= nums.length <= 105
-30 <= nums[i] <= 30
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 
Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
*/

class Solution {
    public int[] productExceptSelf(int[] nums) {
        return solution1(nums);
    }

    private int[] solution1(int[] nums) {
        int n = nums.length;

        int[] result = new int[n];

        int numOfZero = 0;
        int indexOfZero = -1;
        int product = 1;

        // count zero number and remember its index
        for (int i = 0; i < n; ++i) {
            product *= nums[i];
            if (nums[i] == 0) {
                ++ numOfZero;
                indexOfZero = i;
            }
        }

        if (numOfZero == 0) {
            for (int i = 0; i < n; ++i) {
                result[i] = product / nums[i];
            }
        } else if (numOfZero == 1) {
            product = 1;
            for (int i = 0; i < n; ++i) {
                if (i != indexOfZero) {
                    product *= nums[i];
                }
            }
            result[indexOfZero] = product;
        } else { // (numOfZero > 1)
            // do nothing, all item in result[] are zero by default
        }
        return result;
    }
}


