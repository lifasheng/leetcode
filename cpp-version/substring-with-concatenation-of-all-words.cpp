class Solution {
public:
/*
这道题最重要的条件就是L中每个字符的长度相同。
再加上map就很好解决。
*/
    vector<int> findSubstring(string S, vector<string> &L) {
        vector<int> result;
        if (S.empty() || L.empty()) return result;
        
        const int sLen = S.size();
        const int wordLen = L[0].size();
        const int fullWordLen = L.size() * wordLen;
        
        if (sLen < fullWordLen) return result;
        
        // L中每个字符串出现的次数
        unordered_map<string, int> expected;
        for(int i=0; i<L.size(); ++i) {
            expected[L[i]]++;
        }
        
        // 从头到尾扫描一遍
        for(int i=0; i<=sLen-fullWordLen; ++i) {
            unordered_map<string, int> appeared;
            
            // 将当前拼接长度的字符按照L中的字符串长度进行分割，并统计出次数
            for(int j=i; j<i+fullWordLen; j+=wordLen) {
                string ts = S.substr(j, wordLen);
                if (expected.find(ts) != expected.end()) {
                    appeared[ts]++;
                }
            }
            
            // 比较我们遇到字符串出现次数是否和期望的出现次数相同
            bool flag = true;
            for(auto iter=expected.begin(); iter!=expected.end(); ++iter) {
                if (appeared[iter->first] != iter->second) {
                    flag = false;
                    break;
                }
            }
            
            if (flag) {
                result.push_back(i);
            }
        }
        
        return result;
    }
};
