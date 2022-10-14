/*
Easy

Given an array of integers arr, return true if we can partition the array into three non-empty parts with equal sums.

Formally, we can partition the array if we can find indexes i + 1 < j with (arr[0] + arr[1] + ... + arr[i] == arr[i + 1] + arr[i + 2] + ... + arr[j - 1] == arr[j] + arr[j + 1] + ... + arr[arr.length - 1])

Example 1:
Input: arr = [0,2,1,-6,6,-7,9,1,2,0,1]
Output: true
Explanation: 0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1

Example 2:
Input: arr = [0,2,1,-6,6,7,9,-1,2,0,1]
Output: false

Example 3:
Input: arr = [3,3,6,5,-2,2,5,1,-9,4]
Output: true
Explanation: 3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4
 
Constraints:
3 <= arr.length <= 5 * 104
-104 <= arr[i] <= 104
*/

class Solution {
    public boolean canThreePartsEqualSum(int[] arr) {
        return canThreePartsEqualSum_optimized(arr);
    }
    
    /*
    思路： 先计算前缀和，然后遍历前缀和，找到v, 2v, 3v 判断他们的下标是否递增
          这里需要注意的是，可能会有重复的前缀和，所以map的value是保存下标的链表
          
          in some extreme case, it is not optimal, like all values are same, [0, 0, 0, 0, 0 ...]
          it could be O(N^2)
    */
    public boolean canThreePartsEqualSum_usingMap(int[] arr) {
        int n = arr.length;
        int[] prefixSum = new int[n];
        Map<Integer, List<Integer>> sumToIndex = new HashMap<>();
        prefixSum[0] = arr[0];
        sumToIndex.put(prefixSum[0], new ArrayList<>());
        sumToIndex.get(prefixSum[0]).add(0);
        for (int i = 1; i < n; ++i) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
            sumToIndex.putIfAbsent(prefixSum[i], new ArrayList<>());
            sumToIndex.get(prefixSum[i]).add(i);
        }
        
        for (int i = 0; i < n - 2; ++i) {
            int v = prefixSum[i];
            
            if (!sumToIndex.containsKey(v * 2) || !sumToIndex.containsKey(v * 3)) continue;
            
            List<Integer> listJ = sumToIndex.get(v * 2);
            List<Integer> listK = sumToIndex.get(v * 3);
            for (int j : listJ) {
                for (int k : listK) {
                    if (i < j && j < k && k == n - 1) return true;
                }
            }
        }
        
        return false;
    }
    
    // O(N) time, O(1) space
    public boolean canThreePartsEqualSum_optimized(int[] arr) {
        int n = arr.length;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        if (sum % 3 != 0) return false;
        
        int subSum = sum / 3;
        
        boolean firstFound = false;
        boolean secondFound = false;
        boolean thirdFound = false;
        
        int preSum = 0;
        for (int i = 0; i < n; ++i) {
            preSum += arr[i];
            if (!firstFound && preSum == subSum) {
                firstFound = true;
            } else if (firstFound && !secondFound && preSum == (2 * subSum)) {
                secondFound = true;
            } else if (firstFound && secondFound && !thirdFound && i == n - 1) {
                thirdFound = true;
            }
        }
        
        return firstFound && secondFound && thirdFound;
    }
}

