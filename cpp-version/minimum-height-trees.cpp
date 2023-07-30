/*

For an undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given such a graph, write a function to find all the MHTs and return a list of their root labels.

Format
The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).

You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

Example 1 :

Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]

        0
        |
        1
       / \
      2   3 

Output: [1]
Example 2 :

Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

     0  1  2
      \ | /
        3
        |
        4
        |
        5 

Output: [3, 4]
Note:

According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.


*/


//// initial solution, stack overflow when n = 5000

// the idea is for each node, assume it's root, find its max height, and pick the min height amonght these roots.
// for each root, how to find its max height? we go through its neighbors, and use dfs to find the height of each neighbor's branch
// the max height of these neighbors' height is the root's max height.
// in the dfs, just use a cache to avoid re-access if we already have answer.
class Solution {
public:
    vector<int> findMinHeightTrees(int n, vector<vector<int>>& edges) {
        unordered_map<int, set<int>> m;
        for (auto vi : edges) {
            m[vi[0]].insert(vi[1]);
            m[vi[1]].insert(vi[0]);
        }
        
        map<pair<int, int>, int> dist;
        vector<int> roots = {};
        
        int minH = INT_MAX;
        for(int i=0; i<n; ++i) {
            int h = findMaxH(i, m, dist);
            //cout << "maxH for " << i << " is " << h << endl;
            if (h < minH) {
                roots = {i};
                minH = h;
            } else if (h == minH) {
                roots.push_back(i);
            }
        }
        
        return roots;
    }
    
    int findMaxH(int i, unordered_map<int, set<int>> &m, 
            map<pair<int, int>, int> &dist) {
        int maxH = 0;
        for (auto neigh : m[i]) {
            int h = dfs(i, neigh, m, dist);
            maxH = max(h, maxH);
        }
        return maxH;
    }
    
    int dfs(int i, int j, unordered_map<int, set<int>> &m, 
            map<pair<int, int>, int> &dist) {        

        auto pij = make_pair(j, i);
        if (dist.find(pij) != dist.end()) return dist[pij];

        int maxH = 0;
        for (auto neigh : m[j]) {
            if (neigh != i) {
                int h = dfs(j, neigh, m, dist);
                dist[make_pair(neigh, j)] = h;
                maxH = max(h, maxH);
            }
        }
        return maxH + 1;
        
    }
};





