/*
Hard

Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.

Example 1:
Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
Output: 4
Explanation: After the rain, water is trapped between the blocks.
We have two small ponds 1 and 3 units trapped.
The total volume of water trapped is 4.

Example 2:
Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
Output: 10

Constraints:
m == heightMap.length
n == heightMap[i].length
1 <= m, n <= 200
0 <= heightMap[i][j] <= 2 * 104
*/

class Solution {

    private static final int[][] DIRECTIONS = {
        {-1, 0},
        {0, 1},
        {1, 0},
        {0, -1}
    };
    
    /*
    思路: 用最小堆辅助，从四周开始遍历，每次选最小高度的位置，并遍历它的邻居，计算可以装多少水，并将邻居加入最小堆，
         此时邻居的高度应该设为当前位置的高度和邻居高度中的较大者。
    */
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        int n  = heightMap[0].length;
        
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        // 最小堆， key位高度，value为坐标
        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>(
            (p1, p2) -> p1.getKey() - p2.getKey());
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    Pair pair = new Pair(i, j);
                    visited.add(pair);
                    pq.offer(new Pair(heightMap[i][j], pair));
                }
            }
        }
        
        int res = 0;
        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> p1 = pq.poll();
            Pair<Integer, Integer> p2 = p1.getValue();
            
            int i = p2.getKey();
            int j = p2.getValue();
            for (int k = 0; k < DIRECTIONS.length; ++k) {
                int newI = i + DIRECTIONS[k][0];
                int newJ = j + DIRECTIONS[k][1];
                Pair<Integer, Integer> newPair = new Pair(newI, newJ);
                if (newI < 0 || newI >= m || newJ < 0 || newJ >= n 
                    || visited.contains(newPair)) continue;

                // 注意，这里要用pq里面该位置的高度，而不是heightMap的，因为该位置的高度可能已经变高货变低了。
                // res += Math.max(0, heightMap[i][j] - heightMap[newI][newJ]);
                res += Math.max(0, p1.getKey() - heightMap[newI][newJ]);
                
                visited.add(newPair);
                
                // int height = Math.max(heightMap[i][j], heightMap[newI][newJ]);
                int height = Math.max(p1.getKey(), heightMap[newI][newJ]);
                pq.offer(new Pair(height, newPair));
            }
        }
        
        return res;
    }
}

