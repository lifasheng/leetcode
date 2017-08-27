class Solution {
public:
#define M3
#ifdef M1
/*
递归+备忘录
对于字符串中的'0'需要特殊处理。
test case:
"", "1", "12", "10", "101"
*/
    int numDecodings(string s) {
        if (s.empty() || s[0]=='0') return 0;
        return nd(s, 0, s.size());
    }
    unordered_map<int, int> cache;
    int nd(string &s, int first, int last) {
        if (first == last) return 1;  // 对于结尾的情况，返回1, 比如s="1"
        if (s[first] == '0') return 0;  // 对于'0'开头的字符要特殊处理， 比如 s="10", "101"
        
        if (cache.find(first) == cache.end()) {
            int d = 0;
            int r = 0;
            for(int i=first; i!=last; ++i) {
                d = d*10 + s[i]-'0'; // 注意要 减去'0'
                if (d == 0) continue;
                if (d>26) break;
                
                r += nd(s, i+1, last);
            }
            cache[first] = r;
        }
            
        return cache[first];
    }
#endif
#ifdef M2
/*
test case:  "", "1", "12", "10", "101"
DP, 设f(i)表示从i到n-1的decode ways。
为什么从后往前计算呢？ 因为从前往后不好计算:-)，
比如120, 到0的时候，你不好利用前面的结果。
而从后往前就能很方便的利用后面已有的结果。
代码中需要特别注意的是i+2有可能会越界， 比如s="10"
*/
    int numDecodings(string s) {
        if (s.empty() || s[0]=='0') return 0;
        
        const int n = s.size();
        vector<int> f(n);
        f[n-1] = (s[n-1] == '0')?0:1;
        for(int i=n-2; i>=0; --i) {
            if(s[i] == '0') {
                f[i] = 0;
            }
            else {
                f[i] = f[i+1];
                int d = (s[i]-'0')*10+(s[i+1]-'0');
                if (d<=26) f[i] += i+2>=n?1:f[i+2];
            }
        }
        return f[0];
    }
#endif
#ifdef M3
/*
test case:  "", "1", "12", "10", "101"
DP, 设f(i)表示从i到n-1的decode ways。
滚动数组，f(i)其实可以省掉。
*/
    int numDecodings(string s) {
        if (s.empty() || s[0]=='0') return 0;
        
        const int n = s.size();
        int ip1 = (s[n-1] == '0')?0:1;
        int ip2 = 1;
        int fi = ip1; // 对于长度为1的字符串,"1"
        for(int i=n-2; i>=0; --i) {
            if(s[i] == '0') {
                fi = 0;
            }
            else {
                fi = ip1;
                int d = (s[i]-'0')*10+(s[i+1]-'0');
                if (d<=26) fi += ip2;
            }
            
            ip2 = ip1;
            ip1 = fi;
        }
        return fi;
    }
#endif
};





class Solution {
public:
    // 递归+备忘录法
    // 测试用例："", "0"，"123", "2745"
    int numDecodings(string s) {
        // 保存从某个位置到字符串结尾的decode方法个数
        unordered_map<int, int> m;
        return numDecodings(s, 0, m);
    }

    int numDecodings(string &s, int idx, unordered_map<int, int> &m) {
        const size_t n = s.size();
        if (n-idx == 0) return 0; // 空串
        if (!(s[idx] >= '1' && s[idx] <='9')) return 0; // '0'开头
        if (n-idx == 1) return 1; // 单个数字

        if (m.find(idx) != m.end()) return m[idx];

        // 切分出一个数字
        int n1 = numDecodings(s, idx+1, m);

        // 切分出两个数字
        bool b = atoi(s.substr(idx,2).c_str()) <= 26;
        int n2 = 0;
        if (b) {
            // 如果只剩两个数字，则方法数为1，否则继续递归
            n2 = n-idx>2 ? numDecodings(s, idx+2, m) : 1;
        }
        return m[idx] = n1+n2;
    }
};
