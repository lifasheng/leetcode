class Solution {
public:
/*
基本的思路是：
双指针[start, end],动态维护一个区间。尾指针不断往后扫,当扫到有一个窗口包含了所有 T 的字符后,
然后再收缩头指针,直到不能再收缩为止。最后记录所有可能的情况中窗口最小的。
缩减规则为：如果S[start]不在T中 或者 S[start]在T中但是删除后窗口依然可以包含T中的所有字符，那么start = start+1， 直到不满足上述两个缩减规则。
用2个数组来保存各字母出现的次数，因为ASCII 只有256个字符，数组只要开256就够了。
// !!! 注意，我们一旦找到了一个完整包含了一个T的子串，不能将appeared清零
// test case: S="bdab",  T="ab"
*/
    string minWindow(string S, string T) {
        if (S.empty() || S.size() < T.size()) return "";
        
        const int ASCII_MAX = 256;
        int expected_count[ASCII_MAX];
        int appeared_count[ASCII_MAX];
        fill_n(expected_count, ASCII_MAX, 0);
        fill_n(appeared_count, ASCII_MAX, 0);
        
        for(int i=0; i<T.size(); ++i) expected_count[T[i]]++;
        
        int min_width = INT_MAX, min_start = 0; // 最小窗口的大小和起点
        int wnd_start = 0, wnd_end = 0; // 当前窗口的起点和终点
        int appeared = 0; // 完整包含了一个 T
        
        //尾指针不断往后扫描
        for(wnd_end=0; wnd_end < S.size(); ++wnd_end) {
            if (expected_count[S[wnd_end]] > 0) { // 遇到了T中的字符
                appeared_count[S[wnd_end]]++;
                if (appeared_count[S[wnd_end]] <= expected_count[S[wnd_end]]) {
                    ++appeared;
                }
            }
            
            if (appeared == T.size()) { // 完整包含了一个 T
                // 收缩头指针
                while(expected_count[S[wnd_start]] == 0 || appeared_count[S[wnd_start]] > expected_count[S[wnd_start]]) {
                    --appeared_count[S[wnd_start]];
                    ++wnd_start;
                }
                
                if (wnd_end-wnd_start+1 < min_width) {
                    min_width = wnd_end-wnd_start+1;
                    min_start = wnd_start;
                }
            }
        }
        
        if (min_width == INT_MAX) return "";
        return S.substr(min_start, min_width);
    }
};
