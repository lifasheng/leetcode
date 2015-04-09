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
