/*
Hard

You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
Output: [1,1,2,3]
Explanation:
Initially, the 2d grid is filled with water.
- Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
- Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
- Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
- Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.

Example 2:
Input: m = 1, n = 1, positions = [[0,0]]
Output: [1]
 
Constraints:
1 <= m, n, positions.length <= 104
1 <= m * n <= 104
positions[i].length == 2
0 <= ri < m
0 <= ci < n

Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?
*/

// class Solution {
//     /*
//     Test case:
//     2
//     2
//     [[0,0],[1,1],[0,1]]
//     output: [1,2,1]
//     */
//     public List<Integer> numIslands2(int m, int n, int[][] positions) {
//         return numIsland2_1(m, n, positions);
//     }
    
//     // solution 1:  O(k^2), where k is the length of positions, TLE
//     private List<Integer> numIsland2_1(int m, int n, int[][] positions) {
//         List<Integer> result = new ArrayList<>();
//         // 注意，此处可以将pair转化为integer， i * n + j
//         Map<Pair<Integer, Integer>, Integer> positionToId = new HashMap<>();
//         int islandCount = 0;
//         int islandId = 0;
//         for (int k = 0; k < positions.length; ++k) {
//             int i = positions[k][0];
//             int j = positions[k][1];
//             Pair cur = new Pair(i, j);
//             if (positionToId.containsKey(cur)) {
//                 result.add(result.get(result.size() - 1));
//                 continue;
//             }
//             Pair up = new Pair(i - 1, j);
//             Pair down = new Pair(i + 1, j);
//             Pair left = new Pair(i, j - 1);
//             Pair right = new Pair(i, j + 1);
//             Set<Integer> idSet = new HashSet<>();
//             if (i - 1 >= 0 && positionToId.containsKey(up)) {
//                 idSet.add(positionToId.get(up));
//             }

//             if (i + 1 < m && positionToId.containsKey(down)) {
//                 idSet.add(positionToId.get(down));
//             }

//             if (j - 1 >= 0 && positionToId.containsKey(left)) {
//                 idSet.add(positionToId.get(left));
//             }

//             if (j + 1 >= 0 && positionToId.containsKey(right)) {
//                 idSet.add(positionToId.get(right));
//             }
//             if (idSet.size() == 0) {
//                 ++ islandCount;
//                 positionToId.put(cur, ++ islandId);
//             } else if (idSet.size() == 1) {
//                 positionToId.put(cur, idSet.iterator().next());
//             } else {
//                 int rootId = idSet.iterator().next();
//                 for (Map.Entry<Pair<Integer, Integer>, Integer> entry : positionToId.entrySet()) {
//                     Pair<Integer, Integer> pos = entry.getKey();
//                     int id = entry.getValue();
//                     if (idSet.contains(id)) {
//                         positionToId.put(pos, rootId);
//                     }
//                 }
//                 positionToId.put(cur, rootId);
//                 islandCount -= (idSet.size() - 1);
//             }
//             result.add(islandCount);
//         }
//         return result;
//     }
// }


class Solution {
    // O(m*n + k), O(m*n) to initialize UnionFind, and O(k) to process positions.
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m * n);
        List<Integer> result = new ArrayList<>();
        for (int[] pos : positions) {
            int i = pos[0];
            int j = pos[1];
            
            int cur = i * n + j;
            int up = (i - 1) * n + j;
            int down = (i + 1) * n + j;
            int left = i * n + j - 1;
            int right = i * n + j + 1;
            
            if (uf.isValid(cur)) { // 防止同一个position出现多次
                result.add(uf.getCount());
                continue;
            }
            
            uf.setParent(cur);
            if (i - 1 >= 0 && uf.isValid(up)) {
                uf.union(cur, up);
            }
            if (i + 1 < m && uf.isValid(down)) {
                uf.union(cur, down);
            }
            if (j - 1 >= 0 && uf.isValid(left)) {
                uf.union(cur, left);
            }
            if (j + 1 < n && uf.isValid(right)) {
                uf.union(cur, right);
            }
            
            result.add(uf.getCount());
        }
        return result;
    }
        
    class UnionFind {
        private int count;
        private int[] parent;
        
        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; ++i) {
                parent[i] = -1;
            }
        }
        
        public boolean isValid(int i) {
            return parent[i] >= 0;
        }
        
        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI == rootJ) return;
            parent[rootI] = rootJ;
            -- count;
        }
        
        public void setParent(int i) {
            parent[i] = i;
            ++ count;
        }
        
        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }
        
        public int getCount() {
            return count;
        }
    }
}


