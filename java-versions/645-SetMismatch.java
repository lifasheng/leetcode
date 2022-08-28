/*
Easy - Medium

You have a set of integers s, which originally contains all the numbers from 1 to n. Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set, which results in repetition of one number and loss of another number.
You are given an integer array nums representing the data status of this set after the error.
Find the number that occurs twice and the number that is missing and return them in the form of an array.

Example 1:
Input: nums = [1,2,2,4]
Output: [2,3]

Example 2:
Input: nums = [1,1]
Output: [1,2]
 
Constraints:
2 <= nums.length <= 104
1 <= nums[i] <= 104
*/

class Solution {
    public int[] findErrorNums(int[] nums) {
        return findErrorNums_negativeMarking(nums);
    }
    
    // solution 1: use set, O(N) space
    private int[] findErrorNums_useSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int duplicate = 0;
        int sum = 0;
        for (int i : nums) {
            sum += i;
            if (set.contains(i)) {
                duplicate = i;
            } else {
                set.add(i);
            }
        }
        
        int n = nums.length;
        int targetSum = (n * (n + 1)) / 2;
        int diff = targetSum - sum;
        return new int[] {duplicate, duplicate + diff};
    }
    
    // solution 2: negative marking, O(1) space, very good!
    private int[] findErrorNums_negativeMarking(int[] nums) {
        int duplicate = 0;
        for (int i = 0; i < nums.length; ++i) {
            int j = Math.abs(nums[i]); // important to use abs here
            
            if (nums[j - 1] > 0) {
                nums[j - 1] = - nums[j - 1];
            } else {
                duplicate = j;
            }
        }
        
        int missing = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] > 0) {
                missing = i + 1;
                break;
            }
        }
        
        return new int[] {duplicate, missing};
    }
}

