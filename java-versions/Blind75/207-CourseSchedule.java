/*
very very good!

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.

Example 2:
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 
Constraints:
1 <= numCourses <= 2000
0 <= prerequisites.length <= 5000
prerequisites[i].length == 2
0 <= ai, bi < numCourses

*/

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        return canFinish_checkCycle(numCourses, prerequisites);
    }


    /*
    思路： topological sort
    方法是重复寻找一个入度为0的顶点，将该顶点从图中删除（即放进一个队列里存着，这个队列的顺序就是最后的拓扑排序，具体见程序），
    并将该结点及其所有的出边从图中删除（即该结点指向的结点的入度减1），最终若图中全为入度为1的点，则这些点至少组成一个回路。
    
    采用邻接矩阵存储时，遍历二维数组，求各顶点入度的时间复杂度是O(n^2)。 遍历所有结点，找出入度为0的结点的时间复杂度是O(n)。
    对于n个入度为0的结点，删除他们的出边的复杂度为O(n^2)。 所以总的复杂度为<strong>O(n^2)</strong>。
    
    对于邻接表，遍历所有边，求各顶点入度的时间复杂度是O(e)，即边的个数。遍历所有结点，找出入度为0的结点的时间复杂度是O(n)，
    即顶点的个数。遍历所有边，删除入度为0的结点的出边的复杂度为O(e)，即边的个数。所以总的时间复杂度是O(n+e)
    */
    public boolean canFinish_topologicalSort(int numCourses, int[][] prerequisites) {
        int n = prerequisites.length;
        if (n == 0) return true;

        // build graph
        int[] indegree = new int[numCourses];
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; ++i) {
            graph[i] = new LinkedList<>();
        }
        for (int[] pre : prerequisites) {
            int from = pre[1];
            int to = pre[0];
            indegree[to] ++;
            graph[from].add(to);
        }

        // find nodes with indgree == 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; ++i) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        for (int i = 0; i < numCourses; ++i) {
            if (queue.isEmpty()) return false;
            int finished = queue.poll();
            for (int neighbor : graph[finished]) {
                indegree[neighbor] --;
                if (indegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return true;
    }


    /*
    https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/huan-jian--e36de/

    DFS
    用visited数组来记录是否该节点被访问过，这样可以避免重复访问。比如两个节点指向同一个节点的情况。
    （比如 https://labuladong.github.io/algo/images/%E6%8B%93%E6%89%91%E6%8E%92%E5%BA%8F/1.jpeg）

    用onpath数组来记录当前正在访问的路径。
    需要注意的是graph可能存在孤岛（不是连通图），所以要保证对所有节点都访问一遍。

    我们不能只用visited来判断是否有环，因为

         2
       /   \
      1      4
       \   /
         3
     1-> 2, 1->3, 2->4, 3->4

    在这种有向图中，4这个节点会被访问两次，却没有环。

    时间复杂度也是O(n+e)
    */
    private boolean hasCycle;
    private boolean[] visited;
    private boolean[] onPath;
    public boolean canFinish_checkCycle(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        hasCycle = false;
        visited = new boolean[numCourses];
        onPath = new boolean[numCourses];
        for (int i = 0; i < numCourses; ++i) {
            traverse(graph, i);
        }
        
        return !hasCycle;
    }

    private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; ++i) {
            graph[i] = new LinkedList<>();
        }

        for (int[] pre : prerequisites) {
            int from = pre[1];
            int to = pre[0];
            graph[from].add(to);
        }
        return graph;
    }

    private void traverse(List<Integer>[] graph, int node) {
        if (onPath[node]) {
            hasCycle = true;
            return;
        }

        if (visited[node]) return;

        visited[node] = true;

        onPath[node] = true;
        for (int neighbor : graph[node]) {
            traverse(graph, neighbor);
        }
        // 当前节点的所有邻居都访问过了，所以，该以节点为根节点的子树访问过了，可以退回到其父亲节点了。
        onPath[node] = false;
    }
}

