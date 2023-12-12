/*
Hard

very very good! BFS

Given an array of integers arr, you are initially positioned at the first index of the array.
In one step you can jump from index i to index:

i + 1 where: i + 1 < arr.length.
i - 1 where: i - 1 >= 0.
j where: arr[i] == arr[j] and i != j.
Return the minimum number of steps to reach the last index of the array.

Notice that you can not jump outside of the array at any time.

Example 1:
Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
Output: 3
Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.

Example 2:
Input: arr = [7]
Output: 0
Explanation: Start index is the last index. You do not need to jump.

Example 3:
Input: arr = [7,6,9,6,9,6,9,7]
Output: 1
Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
 
Constraints:
1 <= arr.length <= 5 * 104
-108 <= arr[i] <= 108
*/

class Solution {
    // bfs
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;

        Map<Integer, Set<Integer>> numToIndexMap = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            int num = arr[i];
            // 这个优化好像不是必须的，但是能加快一些。
            //if (i > 0 && i + 1 < n && arr[i-1] == num && arr[i+1] == num) continue; 
            numToIndexMap.putIfAbsent(num, new HashSet<>());
            numToIndexMap.get(num).add(i);
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(0);
        visited.add(0);
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                int index = queue.poll();
                if (index == n - 1) return step;

                int left = index - 1;
                if (left >= 0 && !visited.contains(left)) {
                    queue.offer(left);
                    visited.add(left);
                }

                int right = index + 1;
                if (right < n && !visited.contains(right)) {
                    queue.offer(right);
                    visited.add(right);
                }

                Set<Integer> indexes = numToIndexMap.getOrDefault(arr[index], new HashSet<>());
                for (int k : indexes) {
                    if (k == index || visited.contains(k)) continue;
                    queue.offer(k);
                    visited.add(k);
                }
                // 这个优化是必须的，不然会超时。TLE， 因为虽然我们有用visited来去重，但是如果重复元素很多的话，上面这个for循环也是很花时间的。
                numToIndexMap.remove(arr[index]);
            }
            ++ step;
        }
        return -1;
    }
}



