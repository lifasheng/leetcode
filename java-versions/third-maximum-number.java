/*

414. Third Maximum Number
Given a non-empty array of integers, return the third maximum number in this array. If it does not exist, return the maximum number. The time complexity must be in O(n).

Example 1:
Input: [3, 2, 1]

Output: 1

Explanation: The third maximum is 1.
Example 2:
Input: [1, 2]

Output: 2

Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
Example 3:
Input: [2, 2, 3, 1]

Output: 1

Explanation: Note that the third maximum here means the third maximum distinct number.
Both numbers with value 2 are both considered as second maximum.

*/


// [1,2,2,5,3,5]
// [1,2,-2147483648]
// [1,-2147483648,2]
// [-2147483648,1,2]
class Solution {
    // navie solution, not extendable.
    public int thirdMax(int[] nums) {
        long first = nums[0];
        // we can not use Integer.MIN_VALUE, because nums[i] could be Integer.MIN_VALUE.
        long second = Long.MIN_VALUE;
        long third = Long.MIN_VALUE;
        
        for (int i=1; i<nums.length; i++) {
            if (nums[i] > first) {
                third = second;
                second = first;
                first = nums[i];
            } else if (nums[i] > second) {
                if (nums[i] != first) {
                    third = second;
                    second = nums[i];
                }
            } else if (nums[i] >= third) {
                if (nums[i] != second) {
                    third = nums[i];
                }
            }
        }
        if (third != Long.MIN_VALUE) {
            return (int)third;
        } else {
            return (int)first;
        }
    }
    
    public int thirdMax2(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int i : nums) {
            s.add(i);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(s.size(), Collections.reverseOrder());
        for (int i : s) {
            pq.add(i);
        }
        final int n = 3;
        if (s.size() < n) {
            return pq.peek();
        } else {
            for (int i=1; i<n; ++i) {
                pq.poll();
            }
            return pq.peek();
        }
    }
}

