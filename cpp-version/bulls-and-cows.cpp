class Solution {
public:
#if 0
    // ["", ""] => "0A0B"
    // ["1", "0"] => "0A0B"
    // 思路：先找到相同数字的个数，即位置和数字都一致的个数；
    // 对于不相同的，分别保存到数组中，然后排序，再从前往后扫描一遍进行比较
    string getHint(string secret, string guess) {
        size_t bull = 0, cow = 0;
        vector<int> v1, v2;
        for (size_t i=0; i<secret.size(); ++i) {
            if (secret[i] == guess[i]) {
                ++bull;
            }
            else {
                v1.push_back(secret[i]);
                v2.push_back(guess[i]);
            }
        }
        
        sort(v1.begin(), v1.end());
        sort(v2.begin(), v2.end());
        
        const int vlen = v1.size();
        size_t p1 = 0, p2 = 0;
        while(p1 < vlen && p2 < vlen) {
            if (v1[p1] < v2[p2]) {
                ++p1;
            }
            else if (v1[p1] > v2[p2]) {
                ++p2;
            }
            else {
                ++cow;
                ++p1;
                ++p2;
            }
        }
        
        ostringstream oss;
        oss << bull << "A" <<cow << "B";
        return oss.str();
    }
#else
    // 思路：先找到相同数字的个数，即位置和数字都一致的个数；
    // 上面的提交后发现速度比别人的慢太多，虽然通过，但显然还有提高的空间。
    // 本来想用set或multset，但下面的两个case就把他们给beat了。
    // ["1122", "2211"] => "0A4B"
    // ["112", "221"] => "0A2B"
    // 好吧，hash应该不错。
    string getHint(string secret, string guess) {
        size_t bull = 0, cow = 0;
        unordered_map<int, int> m1, m2;
        for (size_t i=0; i<secret.size(); ++i) {
            if (secret[i] == guess[i]) {
                ++bull;
            }
            else {
                m1[secret[i]]++;
                m2[guess[i]]++;
            }
        }
        
        typedef unordered_map<int, int>::iterator Iterator;
        for(Iterator iter=m1.begin(); iter!=m1.end(); ++iter) {
            if (m2.count(iter->first)) {
                cow += min(iter->second, m2[iter->first]);
            }
        }
        
        ostringstream oss;
        oss << bull << "A" <<cow << "B";
        return oss.str();
    }
#endif

};
