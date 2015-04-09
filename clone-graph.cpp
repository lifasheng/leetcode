/**
 * Definition for undirected graph.
 * struct UndirectedGraphNode {
 *     int label;
 *     vector<UndirectedGraphNode *> neighbors;
 *     UndirectedGraphNode(int x) : label(x) {};
 * };
 */
// bfs
class Solution {
public:
    UndirectedGraphNode *cloneGraph(UndirectedGraphNode *node) {
        if (!node) return node;
        
        unordered_map<UndirectedGraphNode*, UndirectedGraphNode*> m;
        unordered_set<UndirectedGraphNode*> s;
        queue<UndirectedGraphNode*> q;
        q.push(node);
        while(!q.empty()) {
            UndirectedGraphNode *n = q.front();
            q.pop();
            
            if (s.find(n) == s.end()) {
                s.insert(n);
                m[n] = new UndirectedGraphNode(n->label);
                for(auto neib: n->neighbors) {
                    q.push(neib);
                }
            }
        }
        
        s.clear();
        q.push(node);
        while(!q.empty()) {
            UndirectedGraphNode *n = q.front();
            q.pop();
            
            if (s.find(n) == s.end()) {
                s.insert(n);
                UndirectedGraphNode *n2 = m[n];
                for(auto neib: n->neighbors) {
                    q.push(neib);
                    (n2->neighbors).push_back(m[neib]);
                }
            }
        }
        
        return m[node];
    }
};
// dfs
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
