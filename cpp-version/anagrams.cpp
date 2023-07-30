class Solution {
public:
//Anagrams 是指两个string的字母顺序不同，但字母个数和出现的次数相同。
    vector<string> anagrams(vector<string> &strs) {
        unordered_map<string, vector<string> >group;
        
        for(int i=0; i<strs.size(); ++i) {
            string s = strs[i];
            string k = s;
            sort(k.begin(), k.end());
            group[k].push_back(s);
        }
        
        vector<string> result;
        typedef unordered_map<string, vector<string> >::iterator Iterator;
        for(Iterator iter=group.begin(); iter!=group.end(); ++iter) {
            if (iter->second.size() > 1) {
                result.insert(result.end(), iter->second.begin(), iter->second.end());
            }
        }
        
        return result;
    }
};
