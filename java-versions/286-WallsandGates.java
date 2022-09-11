/*
Medium

You are given an m x n grid rooms initialized with these three possible values.

-1 A wall or an obstacle.
0 A gate.
INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.

Example 1:
Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]

Example 2:
Input: rooms = [[-1]]
Output: [[-1]]

Constraints:
m == rooms.length
n == rooms[i].length
1 <= m, n <= 250
rooms[i][j] is -1, 0, or 231 - 1.
*/

class Solution {
    private static final int GATE = 0;
    private static final int WALL = -1;
    private static final int EMPTY = (1 << 31) -1;
    private static final List<int[]> DIRECTIONS = Arrays.asList(
        new int[] { 1,  0},
        new int[] {-1,  0},
        new int[] { 0,  1},
        new int[] { 0, -1}
    );
    
    //  有了剪枝，O(M*N) 
    public void wallsAndGates2(int[][] rooms) {
        int m = rooms.length;
        int n = rooms[0].length;
        
        // 用数组代替map，来保存每个empty room到最近gate的距离
        // 思路是对每个gate，先bfs到每个empty room，这样就会出现同一个empty room被多个gate都reach到的情况，我们就剪枝。
        int[] distance = new int[m * n];
        // 保持gate的下标，二维转换为一维下标
        List<Integer> gates = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                int pos = i * n + j;
                if (rooms[i][j] == GATE) {
                    gates.add(pos);
                    distance[pos] = GATE;
                } else if (rooms[i][j] == WALL) {
                    distance[pos] = WALL;
                } else {
                    distance[pos] = EMPTY;
                }
            }
        }
        
        // 从gate开始bfs，更新每个empty room的最短距离
        for (int i = 0; i < gates.size(); ++i) {
            bfs(rooms, gates.get(i), distance);
        }
        
        // 将计算得到的distance写回rooms
        for (int k = 0; k < distance.length; ++k) {
            int i = k / n;
            int j = k % n;
            
            rooms[i][j] = distance[k];
        }
    }
    
    private void bfs(int[][] rooms, int start, int[] distance) {
        int m = rooms.length;
        int n = rooms[0].length;
        Deque<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        int dist = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; ++k) {
                int pos = queue.poll();
                int i = pos / n;
                int j = pos % n;
                
                // 只需要更新empty room的distance，不需要更新wall和gate的位置
                if (rooms[i][j] == EMPTY) {
                    if (distance[pos] > dist) {
                        distance[pos] = dist; // 更新最短距离
                    } else {
                        continue; // 剪枝, 如果从某个gate当当前empty room距离比已经得到的距离更长，则放弃。
                    }
                }
                
                for (int[] direction : DIRECTIONS) {
                    int r = i + direction[0];
                    int c = j + direction[1];
                    int newPos = r * n + c;
                    if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY || visited.contains(newPos)) {
                        continue;
                    }
                    queue.add(newPos);
                    visited.add(newPos);
                }
            }
            
            ++ dist;
        }
    }
    
    // very very good!
    // 这个解法很巧妙，思路是一样的，从gate出发，但是他是把所有gate先都放入queue，然后从queue出发，只找empty room，并更新其距离
    // 这样就能保证每个empty room只被更新一次
    // O(M*N)
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        if (m == 0) return;
        int n = rooms[0].length;
        Queue<int[]> q = new LinkedList<>();
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (rooms[row][col] == GATE) {
                    q.add(new int[] { row, col });
                }
            }
        }
        // 这里都不用visited来辅助，因为我们是inpace地更新empty room的distance，这样就不会重复访问同一个empty room。
        while (!q.isEmpty()) {
            int[] point = q.poll();
            int row = point[0];
            int col = point[1];
            for (int[] direction : DIRECTIONS) {
                int r = row + direction[0];
                int c = col + direction[1];
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                    continue;
                }
                rooms[r][c] = rooms[row][col] + 1;
                q.add(new int[] { r, c });
            }
        }
    }
}


