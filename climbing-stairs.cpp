class Solution {
public:
#define M3
#ifdef M1 // 备忘录法
    int climbStairs(int n) {
        if (n <= 1) return 1;
        static unordered_map<int, int> m;
        
        if (m.find(n-1) == m.end())
            m[n-1] = climbStairs(n-1);
        if (m.find(n-2) == m.end())
            m[n-2] = climbStairs(n-2);
        
        return m[n-1] + m[n-2];
    }
#endif
#ifdef M2 // 斐波那契数列, 用数组保存中间值
    int climbStairs(int n) {
        if (n<=1) return 1;
        
        vector<int> vi;
        vi.push_back(1); // n==0
        vi.push_back(1); // n==1
        for(int i=2; i<=n; ++i) {
            vi.push_back(vi[i-2] + vi[i-1]);
        }
        
        return vi[n];
    }
#endif
#ifdef M3 // 斐波那契数列, 用两个变量保存上一次的值
    int climbStairs(int n) {
        int prev = 0;
        int cur = 1;
        // note: i <= n, not i < n
        for(int i=1; i<=n; ++i) {
            int temp = cur;
            cur += prev;
            prev = temp;
        }
        
        return cur;
    }
#endif
};
