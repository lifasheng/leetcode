/*
Given an array of non-negative integers arr, you are initially positioned at start index of the array. When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach any index with value 0.

Notice that you can not jump outside of the array at any time.

Example 1:
Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation: 
All possible ways to reach at index 3 with value 0 are: 
index 5 -> index 4 -> index 1 -> index 3 
index 5 -> index 6 -> index 4 -> index 1 -> index 3 

Example 2:
Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true 
Explanation: 
One possible way to reach at index 3 with value 0 is: 
index 0 -> index 4 -> index 1 -> index 3

Example 3:
Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation: There is no way to reach at index 1 with value 0.
 

Constraints:
1 <= arr.length <= 5 * 104
0 <= arr[i] < arr.length
0 <= start < arr.length
*/

class Solution {
    /*
    这道题真的很好， very very good!!!
    本质上是一个二叉树的遍历问题，递归就可以解决。

    递归出口是start超出了边界。

    此外还需要防止重复访问同一个位置的情况，这样会出现死循环。所以用一个visited数组来记录访问过的位置。
    */
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return canReach(arr, start, visited);
    }

    private boolean canReach(int[] arr, int start, boolean[] visited) {
        int n = arr.length;

        if (start < 0 || start >= n) return false;

        if (arr[start] == 0) return true;

        if (visited[start]) return false;
        visited[start] = true;

        return canReach(arr, start + arr[start], visited) || canReach(arr, start - arr[start], visited);
    }
}


