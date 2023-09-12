/*
Given a reference of a node in a connected undirected graph.
Return a deep copy (clone) of the graph.
Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.

class Node {
    public int val;
    public List<Node> neighbors;
}
 

Test case format:

For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with val == 1, the second node with val == 2, and so on. The graph is represented in the test case using an adjacency list.
An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.


Example 1:
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explanation: There are 4 nodes in the graph.
1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).

Example 2:
Input: adjList = [[]]
Output: [[]]
Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.

Example 3:
Input: adjList = []
Output: []
Explanation: This an empty graph, it does not have any nodes.
 
Constraints:
The number of nodes in the graph is in the range [0, 100].
1 <= Node.val <= 100
Node.val is unique for each node.
There are no repeated edges and no self-loops in the graph.
The Graph is connected and all nodes can be visited starting from the given node.
*/


/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public Node cloneGraph(Node node) {
        return cloneGraph_bfs2(node);
    }

    private Node cloneGrpha_dfs(Node node) {
        if (node == null) return null;
        Map<Node, Node> map = new HashMap<>();
        return cloneGraph(node, map);
    }

    // https://www.bookstack.cn/read/wind-liang-eetcode/9c3e1a8fa89dc4d7.md
    // O(N +  M), where N is a number of nodes (vertices) and M is a number of edges.
    private Node cloneGraph(Node node, Map<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node newNode = new Node(node.val);
        map.put(node, newNode);

        for (Node neighbor: node.neighbors) {
            newNode.neighbors.add(cloneGraph(neighbor, map));
        }
        return newNode;
    }


    /*
    先来一个简单粗暴的想法。
    首先对图进行一个 BFS，把所有节点 new 出来，不处理 neighbors ，并且把所有的节点存到 map 中。
    然后再对图做一个 BFS，因为此时所有的节点已经创建了，只需要更新所有节点的 neighbors。
    */
    private Node cloneGraph_bfs1(Node node) {
        if (node == null) return null;

        Map<Node, Node> map = new HashMap<>();
        Deque<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.offer(node);
        visited.add(node);
        while(!queue.isEmpty()) {
            Node oldNode = queue.poll();
            Node newNode = new Node(oldNode.val);
            map.put(oldNode, newNode);
            for (Node neighbor : oldNode.neighbors) {
                if (visited.contains(neighbor)) continue;

                queue.offer(neighbor);
                visited.add(neighbor);
            }
        }

        queue.clear();
        visited.clear();
        queue.offer(node);
        visited.add(node);
        while(!queue.isEmpty()) {
            Node oldNode = queue.poll();
            Node newNode = map.get(oldNode);
            for (Node neighbor : oldNode.neighbors) {
                newNode.neighbors.add(map.get(neighbor));

                if (visited.contains(neighbor)) continue;
                queue.offer(neighbor);
                visited.add(neighbor);
            }
        }

        return map.get(node);
    }

    /*
    当然再仔细思考一下，其实我们不需要两次 BFS。
    我们要解决的问题是遍历当前节点的时候，邻居节点没有生成，那么我们可以一边遍历一边生成邻居节点，就可以同时更新 neighbors了。
    */
    private Node cloneGraph_bfs2(Node node) {
        if (node == null) return null;

        Map<Node, Node> map = new HashMap<>();
        Deque<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.offer(node);
        visited.add(node);
        while(!queue.isEmpty()) {
            Node oldNode = queue.poll();
            if (!map.containsKey(oldNode)) {
                map.put(oldNode, new Node(oldNode.val));
            }

            Node newNode = map.get(oldNode);
            
            for (Node neighbor : oldNode.neighbors) {
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                }

                newNode.neighbors.add(map.get(neighbor));

                if (visited.contains(neighbor)) continue;

                queue.offer(neighbor);
                visited.add(neighbor);
            }
        }

        return map.get(node);
    }
}


