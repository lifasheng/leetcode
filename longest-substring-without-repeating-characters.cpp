class Solution {
public:
/*
http://www.acmerblog.com/leetcode-longest-substring-without-repeating-characters-5318.html
基本思路是使用哈希，和大部分字符统计或字符重复的思路一样。
再使用两个指针 ，一个指针为start指向当前遍历的字串的开始位置，如果遇到重复字符x，就将start的位置改为上一个x位置+1.
例如：对于 “abcdbef” 初始 start=0, 当遍历到 第二个 b时，检测到重复。上一个b的位置是1. 因此 start=b+1。 然后还要更新hash。
O(n) time, O(1) space
*/
    int lengthOfLongestSubstring(string s) {
        vector<int> hash(256, -1);
        
        int start = 0, ans = 0;
        int i;
        for(i=0; i<s.size(); i++) {
            if( -1 != hash[ s[i] ] ) { //遇到重复字符
                if(ans < i-start) ans = i-start; // 更新子串长度
                for(int j=start; j<hash[ s[i] ]; j++) hash[s[j]] = -1; // 将前面的字符标记为未访问过
                start = hash[ s[i] ] +1; // 更新起位置
            }
            hash[ s[i]] = i; // 标记该元素已经遇到
        }
        if(ans < i-start) ans = i-start; // 尾部没有重复字符的substring
        return ans;
    }
};
