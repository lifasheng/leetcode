class Solution {
public:
/*
test case: ["a", ""], ["", "a*"], ["a", "ab*"], ["a", ".*..a*"]， ["", ".a*"]
注意： 
1. s为空串时，p不一定是空串，因为有*,如["", "a*"];
2. 如果*p == '.',则一定要判断*s != '\0'， 否则["", ".a*"], ["", ".*a"]这些情况下，s+1后会越界;
*/
    bool isMatch(const char *s, const char *p) {
        if (!s && !p) return true;
        if (!s || !p) return false;
        if (*p == '\0') return *s == '\0';
        
        //如果下一个字符不是*，则当前字符必须匹配
        if (*(p+1) != '*') {
            if ( (*p == *s) || (*p == '.' && *s != '\0') ) {
                return isMatch(s+1, p+1);
            }
            else {
                return false;
            }
        }
        else { //下一个字符是*
            while ( (*p == *s) || (*p == '.' && *s != '\0')) {
                //先跳过*以及之前的那个字符，看看是否能匹配。如["abc","a*abc"]
                if (isMatch(s, p+2)) {
                    return true;
                }
                else {
                    // i.e. ["aaabc", "a*bc"]
                    ++s;
                }
            }
            // i.e. ["bc", "a*bc"]
            return isMatch(s, p+2);
        }
    }
};
