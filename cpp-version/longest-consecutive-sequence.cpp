class Solution {
public:
    int longestConsecutive(vector<int> &num) {
        unordered_map<int, bool> used; // hash map
        for(int i=0;i<num.size();++i) {
            used[num[i]] = false;
        }
        
        int longestLength = 0;
        for (int i=0; i<num.size(); ++i) {
            int cur = num[i];
            if(used[cur]) continue;
            
            used[cur] = true;
            int length = 1;
            
            for(int j=cur+1; used.find(j) != used.end(); ++j) {
                ++length;
                used[j] = true;
            }
            for(int j=cur-1; used.find(j) != used.end(); --j) {
                ++length;
                used[j] = true;
            }
            longestLength = max(longestLength, length);
        }
        
        return longestLength;
    }
};
