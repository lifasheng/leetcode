class Solution {
public:
/*
思路： 层次遍历进行搜索。由于是层次遍历，所以如果一旦到达了目标字符串，则当前深度就是最小的。
http://www.cnblogs.com/TenosDoIt/p/3443512.html
本题bfs要注意的问题：
（1）和当前单词相邻的单词是：对当前单词改变一个字母且在字典中存在的单词;
（2）找到一个单词的相邻单词，加入bfs队列后，要从字典中删除（lfs：或将其设置成已访问），因为不删除的话会造成类似于hog->hot->hog的死循环。
而删除对求最短路径没有影响，因为我们第一次找到该单词肯定是最短路径，即使后面其他单词也可能转化得到它，路径肯定不会比当前的路径短（如果要输出所有最短路径，则不能立即从字典中删除，具体见下一题， word-ladder-ii）
bfs队列中用NULL来标识层与层的间隔，每次碰到层的结尾，遍历深度+1
lfs： 层次遍历有几种方法，具见二叉树的层次遍历。
（1）用NULL标识每一层的结尾;
（2）用一个指针指向每一层的最后一个元素;
（3）用两个queue：cur，next
*/
    vector<string> state_extend(string &s, unordered_set<string> &visited, unordered_set<string> &dict) {
        vector<string> result;
        for(size_t i=0; i<s.size(); ++i) {
            for(char c='a'; c<='z'; ++c) {
                if (s[i] != c) {
                    swap(s[i], c);
                    if (dict.count(s) && !visited.count(s)) { // 在字典中并且没访问过
                        visited.insert(s);
                        result.push_back(s);
                    }
                    swap(s[i], c);
                }
            }
        }
        return result;
    }
    
    int ladderLength(string start, string end, unordered_set<string> &dict) {
        unordered_set<string> visited; // 用于去重
        queue<string> cur, next; // 当前层和下一层
        int level = 1; // 当前第几层
        
        visited.insert(start);//不能再扩展到起始结点了。
        cur.push(start);
        while(!cur.empty()) {
            while(!cur.empty()) {
                string s = cur.front();
                cur.pop();
                
                if (s == end) return level;
                
                vector<string> exts = state_extend(s, visited, dict);
                for(size_t i=0; i<exts.size(); ++i) {
                    next.push(exts[i]);
                }
            }
            swap(cur, next);
            ++level;
        }
        
        return 0;
    }
};
