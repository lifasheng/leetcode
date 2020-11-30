/*
60. Permutation Sequence
Hard
The set [1, 2, 3, ..., n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order, we get the following sequence for n = 3:

"123"
"132"
"213"
"231"
"312"
"321"
Given n and k, return the kth permutation sequence.

 

Example 1:

Input: n = 3, k = 3
Output: "213"
Example 2:

Input: n = 4, k = 9
Output: "2314"
Example 3:

Input: n = 3, k = 1
Output: "123"
 

Constraints:

1 <= n <= 9
1 <= k <= n!

*/


class Solution {
    
    private void solve(int n, int k, int[] factorial, List<Integer> digits, StringBuilder sb) {
        if (n==1) {
            sb.append((char) ('0' + digits.get(0)));
            return;
        }    
        
        // find the right position for first digit
        int index = k/factorial[n-1];
        if (k%factorial[n-1] == 0) {
            --index;
        }
        sb.append((char) ('0' + digits.get(index)));
        digits.remove(index); // important
        
        k -= factorial[n-1] * index;
        
        solve(n-1, k, factorial, digits, sb);
    }
    
    // https://www.youtube.com/watch?v=W9SIlE2jhBQ
    // Time: O(N), Space: O(N)
    public String getPermutation1(int n, int k) {
        // step 1: prepare factorial of 1 .. n
        int[] factorial = new int[n+1];
        int f = 1;
        factorial[0] = 1; // not used
        for(int i=1; i<=n; ++i) {
            f = f*i;
            factorial[i] = f;
        }
        
        // step 2: prepare digits list, List is easier to remove element
        List<Integer> digits = new ArrayList<>();
        for (int i=1; i<=n; ++i) {
            digits.add(i);
        }
        
        StringBuilder sb = new StringBuilder();
        
        // step 3: recursive to solve the problem
        solve(n, k, factorial, digits, sb);
        
        return sb.toString();
    }
    
    public String getPermutation(int n, int k) {
        return getPermutation1(n, k);
    }
}


