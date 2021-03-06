/**
 * Definition for undirected graph.
 * struct UndirectedGraphNode {
 *     int label;
 *     vector<UndirectedGraphNode *> neighbors;
 *     UndirectedGraphNode(int x) : label(x) {};
 * };
 */
// dfs, 类似于二叉数的递归。
class Solution {
public:
    UndirectedGraphNode *cloneGraph(UndirectedGraphNode *node) {
        if (!node) return node;
        
        unordered_map<UndirectedGraphNode*, UndirectedGraphNode*> m;
        return clone(node, m);
    }
    UndirectedGraphNode *clone(UndirectedGraphNode *node, unordered_map<UndirectedGraphNode*, UndirectedGraphNode*> &m) {
        if (m.find(node) != m.end()) return m[node];
        
        UndirectedGraphNode *n2 = new UndirectedGraphNode(node->label);
        m[node] = n2;
        for(auto nbr: node->neighbors) {
            n2->neighbors.push_back(clone(nbr, m));
        }
        return m[node];
    }
};



/*
Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph. Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.

 

Example:

Input:
{"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4","neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}

Explanation:
Node 1's value is 1, and it has two neighbors: Node 2 and 4.
Node 2's value is 2, and it has two neighbors: Node 1 and 3.
Node 3's value is 3, and it has two neighbors: Node 2 and 4.
Node 4's value is 4, and it has two neighbors: Node 1 and 3.

 

Note:

    The number of nodes will be between 1 and 100.
    The undirected graph is a simple graph, which means no repeated edges and no self-loops in the graph.
    Since the graph is undirected, if node p has node q as neighbor, then node q must have node p as neighbor too.
    You must return the copy of the given node as a reference to the cloned graph.

*/



// method 1

/*
// Definition for a Node.
class Node {
public:
    int val;
    vector<Node*> neighbors;

    Node() {}

    Node(int _val, vector<Node*> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/

// we go through the whole graph and clone the node first;
// we use a map to save the cloned node, it also helps to avoid accessing a node again if it has been accessed.
// after we cloned, we connect the cloned nodes according to original graph connection.
class Solution {
public:
    Node* cloneGraph(Node* node) {
        if (node == NULL) return NULL;
        
        unordered_map<Node*, Node*> m;
        find_all_nodes_and_clone(node, m);
        connect_cloned_nodes(m);
        
        return m[node];
    }
    
    // recursively clone each node
    void find_all_nodes_and_clone(Node* node, unordered_map<Node*, Node*> &m) {        
        if (m.find(node) != m.end()) return;
        
        m[node] = new Node(node->val, vector<Node*>());
        for (auto neighbor : node->neighbors) {
            find_all_nodes_and_clone(neighbor, m);
        }
    }
    
    void connect_cloned_nodes(unordered_map<Node*, Node*> &m) {
        for (auto entry : m) {
            Node* node = entry.first;
            for (auto neighbor: node->neighbors) {
                m[node]->neighbors.push_back(m[neighbor]);
            }
        }
    }
};



// bfs
// 我们需要保证clone后的邻居顺序也不能变，比如1：2-4, 2: 3-1, 4: 3-1, 3: 2-4
// 当然我们也可以像上面的方法一下，先bfs找到所有的节点，并clone，然后connect cloned的节点。但这样每个节点要访问两次。
// 我们只能在访问邻居的时候，如果该邻居还没有被clone，就当时clone它，这样才能保证新的节点的neighbors数组中的邻居顺序。
// 队列里只保存已经clone过的节点，我们只需要处理它的邻居就行了。
class Solution {
public:
    Node* cloneGraph(Node* node) {
        if (node == NULL) return NULL;

        unordered_map<Node*, Node*> m;
        queue<Node*> q;

        m[node] = new Node(node->val);
        q.push(node);
        while(!q.empty()) {
            Node *n = q.front();
            q.pop();

            for(auto nbr: n->neighbors) {
                if (m.find(nbr) == m.end()) {
                    m[nbr] = new Node(nbr->val);
                    q.push(nbr);
                }
                m[n]->neighbors.push_back(m[nbr]);
            }
}

        return m[node];
    }
};
