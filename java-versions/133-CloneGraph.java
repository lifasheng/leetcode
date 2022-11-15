/*
Medium

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
        return cloneGraph_dfs(node);
    }
    
    // O(N +  M), where N is a number of nodes (vertices) and M is a number of edges.
    Map<Node, Node> map = new HashMap<>();
    public Node cloneGraph_dfs(Node node) {
        if (node == null) {
            return null;
        }
        
        if (map.containsKey(node)) {
            return map.get(node);
        }
        Node clonedNode = new Node(node.val);
        map.put(node, clonedNode);
        for (Node neighbor : node.neighbors) {
            Node clonedNeighbor = cloneGraph(neighbor);
            clonedNode.neighbors.add(clonedNeighbor);
        }
        return clonedNode;
    }
    
    
    public Node cloneGraph_bfs(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> clonedMap = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();
            Node clonedNode = new Node(oldNode.val);
            clonedMap.put(oldNode, clonedNode);
            for (Node neighbor : oldNode.neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        
        visited.clear();
        queue.offer(node);
        visited.add(node);
        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();
            Node clonedNode = clonedMap.get(oldNode);
            for (Node neighbor : oldNode.neighbors) {
                clonedNode.neighbors.add(clonedMap.get(neighbor));
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        
        return clonedMap.get(node);
    }
    
    // 访问每个节点时，它已经被clone过了，只是需要填充neighbors就行了。
    // map既可以保存clone node，又被用来当set
    // O(N +  M)
    public Node cloneGraph_bfs2(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> clonedMap = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);
        clonedMap.put(node, new Node(node.val));
        
        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();
            for (Node neighbor : oldNode.neighbors) {
                if (!clonedMap.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    clonedMap.put(neighbor, new Node(neighbor.val));
                }
                clonedMap.get(oldNode).neighbors.add(clonedMap.get(neighbor));
            }
        }
        
        return clonedMap.get(node);
    }
}

