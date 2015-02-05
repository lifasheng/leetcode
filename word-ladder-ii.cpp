class Solution {
public:
/*
像这种求最短路径、树最小深度问题bfs最适合，因为到了某一层匹配上，则该层就是最短的;
dfs由于遍历了所有的可能性（因为要找到所有最短路径）,在这种情况下不如bfs。
http://www.cnblogs.com/TenosDoIt/p/3443512.html
本题bfs要注意的问题： 一、 怎样保证求得所有的最短路径；二、 怎样构造这些路径
第一个问题：
不能像上一题那样，找到一个单词相邻的单词后就立马把它从字典里删除，因为当前层还有其他单词可能和该单词是相邻的，这也是一条最短路径，
比如hot->hog->dog->dig和hot->dot->dog->dig，找到hog的相邻dog后不能立马删除，因为和hog同一层的单词dot的相邻也是dog，两者均是一条最短路径。
          hot
         /   \
       hog   dot
         \   /
          dog
但是为了避免进入死循环，在hog、dot这一层的单词遍历完成后dog还是得从字典中删除。即等到当前层所有单词遍历完后，和他们相邻且在字典中的单词要从字典中删除。
如果像上面那样没有立马删除相邻单词，就有可能把同一个单词加入bfs队列中，这样就会有很多的重复计算（比如上面例子提到的dog就会被2次加入队列）。因此我们用一个set来保证加入队列中的单词不会重复，set在每一层遍历完清空。
当某一层的某个单词转换可以得到end单词时，表示已经找到一条最短路径，那么该单词的其他转换就可以跳过。并且遍历完这一层以后就可以跳出循环，因为再往下遍历，肯定会超过最短路径长度。
第二个问题：
为了输出最短路径，我们就要在比bfs的过程中保存好前驱节点。每个单词的前驱节点有可能不止一个，那么每个单词就需要一个数组来保存前驱节点。
*/
    vector<string> state_extend(const string &str, unordered_set<string> &visited, unordered_set<string> &dict) {
        vector<string> result;
        string s(str);
        for(size_t i=0; i<s.size(); ++i) {
            for(char c='a'; c<='z'; ++c) {
                if (s[i] != c) {
                    swap(s[i], c);
                    if(dict.count(s) && !visited.count(s)) {
                        //visited.insert(s); // 注意，和word-ladder不同，不能在这里去重，否则有些路径会丢失。
                        result.push_back(s);
                    }
                    swap(s[i], c);
                }
            }
        }
        return result;
    }
    vector<vector<string>> findLadders(string start, string end, unordered_set<string> &dict) {
        unordered_set<string> cur, next; // 当前层和下一层，这里用集合是为了去重，避免同一个string被扩展了两次时造成重复计算。
        unordered_set<string> visited; // 这个去重是为了避免同一层之间互相扩展。
        unordered_map<string, vector<string> > parent; // 记录当前结点的前驱结点
        vector<vector<string> > result;
        bool found = false;
        //int level = 1;
        
        visited.insert(start); // 不能再扩展到起始结点了。
        cur.insert(start);
        while(!cur.empty()) {
            for(auto &s: cur) { // 访问当前层元素
                if (s == end) {
                    found = true; 
                    // if we only need shortes path length, we can return length here.
                    continue;   //如果到达了目标，则不用再扩展当前结点了。
                }
                
                vector<string> exts = state_extend(s, visited, dict);
                for(size_t i=0; i<exts.size(); ++i) {
                    next.insert(exts[i]);
                    parent[exts[i]].push_back(s);
                }
            }
            
            if (found) { // 此时当前层都访问完了，如果到达了end，则所有的最短路径都得到了，后面的路径不可能更短了，所以没必要再访问了。
                break;
            }
            
            /* 
            将下一层都标记为已访问。
            注意，这里是和word ladder最大的不同之处：
            在word ladder中，在对当前层的结点进行扩展时，立刻将扩展出的结点ext标记为已访问，这样别的结点就不会再扩展出这个ext结点了。
            因为它只是求最短路径长度，所以，只要求出任何一个最短路径的长度就OK。
            
            而在本题中，在对当前层的结点进行扩展时，不能立刻将扩展出的结点ext标记为已访问，否则可能会导致其它的最短路径访问不到。
            * 所以，我们可以在当前层的所有结点都扩展完之后，再将所有扩展出的下一层结点标记为已访问，这样就可以保证所有可能的最短路径都访问到。
            
            * 同时最重要的是可以避免下一层的结点之间互相扩展。 如果同一层之间互相扩展，
              对于同一层的结点之间互相扩展，如果end在dict中，不会影响找到最短路径长度（因为第一次遇到end时肯定是最短路径，而一旦遇到，while循环就会退出），            但是对于构造最短路径时，可能会出现死循环。
                   举个例子："hot", "dog", {"hot","dog","hog","hop"}; hot->hog->dog/hop; hot->hop->hog。 除非在构造最短路径时，把路径长度考虑进去，
                    但那样会比较复杂。
            
              对于同一层的结点之间互相扩展，如果end不再dict中，比如输入："hot", "dor", {"hot","dog","hog","hop"};则while循环就退不出去了，就死循环了。
              
            * 另外要说明的是，本题的框架也适word ladder，
            */
            for (auto &s: next) {
                visited.insert(s);
            }
            
            cur.clear(); // 上面的for循环只是访问cur的元素，并没有删除，所以这里要清空。
            swap(cur, next);
            // ++level; // if we only need shortes path length.
        }
        
        if (found) {
            vector<string> path;
            generatePath(start, end, path, parent, result);
            return result;
        }
        
        return result;
    }
    
    void generatePath(const string &start, const string &word, vector<string> &path, 
                unordered_map<string, vector<string> > &parent, vector<vector<string> > &result) 
    {
        path.push_back(word);
        if (word == start) {
            result.push_back(path); // 注意，这里不能直接翻转path，因为path保存了中间结果，回溯的时候还会pop_back，如果翻转了就乱了。
            reverse(result.back().begin(), result.back().end()); 
        }
        else {
            vector<string> &pv = parent[word];
            for(size_t i=0; i<pv.size(); ++i) {
                generatePath(start, pv[i], path, parent, result);
            }
        }
        path.pop_back();
    }
};
