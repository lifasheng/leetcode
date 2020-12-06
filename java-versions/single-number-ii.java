/*
137. Single Number II
Medium
Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.

Example 1:

Input: nums = [2,2,3,2]
Output: 3
Example 2:

Input: nums = [0,1,0,1,0,1,99]
Output: 99
 

Constraints:

1 <= nums.length <= 3 * 104
-231 <= nums[i] <= 231 - 1
Each element in nums appears exactly three times except for one element which appears once.
 

Follow up: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
*/

class Solution {
    // use HashMap, Time: O(N), Space: O(N)
    public int singleNumber1(int[] nums) {
        Map<Integer, Integer> m = new HashMap<>();
        for(int i : nums) {
            m.put(i, m.getOrDefault(i, 0)+1);
        }
        for(int i: nums) {
            if (m.get(i) == 1) {
                return i;
            }
        }
        throw new RuntimeException("bad request");
    }
    
    // use HashSet, Time: O(N), Space: O(N)
    public int singleNumber2(int[] nums) {
        Set<Integer> s = new HashSet<>();
        Long arraySum = 0L;
        for(int i : nums) {
            arraySum += i;
            s.add(i);
        }
        Long setSum = 0L;
        for(int i: s) {
            setSum += i;
        }
        return (int)((3 * setSum - arraySum)/2);
    }
    
    // Time: O(32*N), Space: O(1)
    // Its average time = best = worst case 
    // 使用一个32位的数组来表示在第i位出现1的次数。如果count[i]是3的整数倍，则忽略；否则就把该位取出来组成答案。
    public int singleNumber3(int[] nums) {
        int[] count = new int[32];
        for(int i : nums) {
            for(int j=0; j<32; ++j) {
                count[j] += (i >> j) & 1;
                count[j] %= 3;
            }
        }
        
        int result = 0;
        for(int j=0; j<32; ++j) {
            result += count[j] << j;
        }
        return result;
    }
    
    // slower then singleNumber3, because we need to access nums frequently?
    // Time: O(32*N), Space: O(1)
    public int singleNumber3_2(int[] nums) {
        int result = 0;
        for(int j=0; j<32; ++j) {
            int sumOfBitJ = 0;
            for(int i : nums) {
                sumOfBitJ += (i >> j) & 1;
                sumOfBitJ %= 3;
            }
            result += sumOfBitJ << j;
        }

        return result;
    }
    
    // sorting + single pass scan
    // Time: O(NlogN), note that the worst case is when the array length is N = INT_MAX, which is O(32*N), because INT_MAX = 2^32
    // but, its average and best case should be much better than O(32*N), so it is actually faster than above singleNumber3 method.
    // https://www.youtube.com/watch?v=cOFAmaMBVps
    public int singleNumber4(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        
        Arrays.sort(nums);
        
        // single number in first position case
        if (nums[0] != nums[1]) {
            return nums[0];
        }
        
        // single number in last position case
        if (nums[n-1] != nums[n-2]) {
            return nums[n-1];
        }
        
        // single number in middle case: here we can jump by 3 steps
        for(int i=1; i<n; i+=3) {
            if (nums[i] != nums[i-1]) {
                return nums[i-1];
            }
        }
        
        return -1;
    }
    
    
    // https://www.cnblogs.com/grandyang/p/4263927.html
    // bit xor, Time: O(N), Space: O(1)
    // I still don't fully get the idea, just copy it here.
    // Maybe someday I can understand it.
    public int singleNumber5(int[] nums) {
            int seenOnce = 0, seenTwice = 0;
            for (int num : nums) {
              // first appearence: 
              // add num to seen_once 
              // don't add to seen_twice because of presence in seen_once

              // second appearance: 
              // remove num from seen_once 
              // add num to seen_twice

              // third appearance: 
              // don't add to seen_once because of presence in seen_twice
              // remove num from seen_twice
              seenOnce = ~seenTwice & (seenOnce ^ num);
              seenTwice = ~seenOnce & (seenTwice ^ num);
            }

            return seenOnce;
    }
    
    public int singleNumber(int[] nums) {
        return singleNumber4(nums);
    }
}


