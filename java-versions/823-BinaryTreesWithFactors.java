/*
Given an array of unique integers, arr, where each integer arr[i] is strictly greater than 1.

We make a binary tree using these integers, and each number may be used for any number of times. Each non-leaf node's value should be equal to the product of the values of its children.

Return the number of binary trees we can make. The answer may be too large so return the answer modulo 109 + 7.

 

Example 1:

Input: arr = [2,4]
Output: 3
Explanation: We can make these trees: [2], [4], [4, 2, 2]
Example 2:

Input: arr = [2,4,5,10]
Output: 7
Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].
*/

class Solution {
    private static long MOD = 1_000_000_007;
    public int numFactoredBinaryTrees(int[] arr) {
        return numFactoredBinaryTrees_dp(arr);
    }
    
    // dp[index] = dp[i] * dp[j], arr[index] == arr[i] * arr[j]
    private int numFactoredBinaryTrees_dp(int[] arr) {
        Arrays.sort(arr); // make sure arr is sorted increasingly
        
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            indexMap.put(arr[i], i);
        }
        
        long[] dp = new long[arr.length];
        Arrays.fill(dp, 1); // initial value is 1 because each node is a tree by itself
        
        
        for (int index = 0; index< arr.length; ++index) {
            for (int i = 0; i < index; ++i) {
                if (arr[index] % arr[i] == 0) {
                    int other = arr[index] / arr[i];
                    if (indexMap.containsKey(other)) {
                        int j = indexMap.get(other); // arr[i] * arr[j] == arr[index]
                        dp[index] += dp[i] * dp[j];
                    }
                }
            }
        }
        
        long result = 0;
        for (int i = 0; i < dp.length; ++i) {
            result += dp[i];
        }
        
        return (int)(result % MOD);
    }
    
    private int numFactoredBinaryTrees_memo(int[] arr) {
        Arrays.sort(arr); // make sure arr is sorted increasingly
        
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            indexMap.put(arr[i], i);
        }
        
        // map to cache result for each number: how many ways to construct a tree with this number as root
        Map<Integer, Long> factorMap = new HashMap<>();
        
        long result = 0;
        for (int i = 0; i < arr.length; ++i) {
            result += factor(arr, i, indexMap, factorMap);
        }
        return (int) (result % MOD);
    }
    
    // recursive + memo
    private long factor(int[] arr, int index, 
                       Map<Integer, Integer> indexMap, 
                       Map<Integer, Long> factorMap) {
        if (factorMap.containsKey(arr[index])) return factorMap.get(arr[index]);
        
        long count = 1;
        
        for (int i = 0; i < index; ++i) {
            if (arr[index] % arr[i] == 0) {
                int n = arr[index] / arr[i];
                if (indexMap.containsKey(n)) {
                    int j = indexMap.get(n);   // arr[i] * arr[j] == arr[index]
                
                    count += factor(arr, i, indexMap, factorMap) * factor(arr, j, indexMap, factorMap);
                }
            }
        }
        
        factorMap.put(arr[index], count);
        return count;
    }
}


