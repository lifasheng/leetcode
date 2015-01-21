class Solution {
public:
#if 0
// 先扫描一遍，再从后往前, 相当于两遍
// O（n）time, O(1) space
    int lengthOfLastWord(const char *s) {
        if (!s || *s == '\0') return 0;
        
        // 先到最后
        const char *p = s;
        while(*p) {
            ++p;
        }
        
        int len = 0;
        --p;
        //再从后往前扫描找到第一个word退出
        while(p >= s) {
            if (*p == ' ') {
                if (len) {
                    break;
                }
            }
            else {
                ++len;
            }
            --p;
        }
        return len;
    }
#else
/*
从前往后扫描一遍。
遇到空格就跳过该空格，并判断其下一个字符是否为非空格，若是，则len清零。
遇到非空格就len+1
*/
    int lengthOfLastWord(const char *s) {
        if(!s || *s == '\0') return 0;
        
        int len = 0;
        while(*s) {
            if (*s != ' ') {
                ++s;
                ++len;
            }
            else {
                ++s; // 跳过 ' '
                if (*s && *s != ' ') {
                    len = 0;
                }
            }
        }
        
        return len;
    }
#endif
};
