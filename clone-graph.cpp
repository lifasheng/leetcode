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

// bfs
// 对于在map中存在的结点，它肯定已经被访问过了，所以不用再加入队列了。
// 而在队列中的结点，肯定是已经clone过了，所以只需要处理它的邻居就行了。
class Solution {
public:
    UndirectedGraphNode *cloneGraph(UndirectedGraphNode *node) {
        if (!node) return node;
        
        unordered_map<UndirectedGraphNode*, UndirectedGraphNode*> m;
        queue<UndirectedGraphNode*> q;
        q.push(node);
        m[node] = new UndirectedGraphNode(node->label);
        while(!q.empty()) {
            UndirectedGraphNode *n = q.front();
            q.pop();
            
            for(auto nbr: n->neighbors) {
                if (m.find(nbr) != m.end()) {
                    m[n]->neighbors.push_back(m[nbr]);
                }
                else {
                    m[nbr] = new UndirectedGraphNode(nbr->label);
                    m[n]->neighbors.push_back(m[nbr]);
                    q.push(nbr);
                }
            }
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
class Solution {
public:
    Node* cloneGraph(Node* node) {
        if (node == NULL) return NULL;
        
        unordered_map<Node*, Node*> m;
        
        find_all_nodes(node, m);
        
        return cloneGraph(node, m);
    }
    
    void find_all_nodes(Node* node, unordered_map<Node*, Node*> &m) {
        if (node == NULL) return;
        
        if (m.find(node) != m.end()) return;
        
        m[node] = NULL;
        for (auto neighbor : node->neighbors) {
            find_all_nodes(neighbor, m);
        }
    }
    
    Node* cloneGraph(Node* node, unordered_map<Node*, Node*> &m) {
        for (auto entry : m) {
            Node* node = entry.first;
            m[node] = new Node(node->val, vector<Node*>());
        }
        
        for (auto entry : m) {
            Node* node = entry.first;
            for (auto neighbor: node->neighbors) {
                m[node]->neighbors.push_back(m[neighbor]);
            }
        }
        return m[node];
    }
};
