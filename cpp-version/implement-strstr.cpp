class Solution {
public:
// test case: [NULL, NULL], ["a", NULL"], [NULL, "a"], ["", ""], ["a", ""], ["", "a"], ["a", "a"], ["a", "ab"], ["ab", "b"]
// ["", ""] and ["a", ""] should return 0
#define M1
#ifdef M1
// O(n*m) time, O(1) space
    int strStr(char *haystack, char *needle) {
        if (!needle) return 0;
        if (!haystack) return -1;
        
        int N = strlen(haystack);
        int M = strlen(needle);
        
        for(int i=0; i<= N-M; ++i) {
            char *p1 = haystack+i;
            char *p2 = needle;
            
            while(*p1 && *p2 && *p1 == *p2) { // *p1 && *p2 对["",""]是很重要的.
                ++p1;
                ++p2;
            }
            
            if(!*p2) {
                return i;
            }
        }
        
        return -1;
    }
#endif
#ifdef M2 // 用下标而不是指针
    int strStr(char *haystack, char *needle) {
        if (!needle) return 0;
        if (!haystack) return -1;
        int N = strlen(haystack);
        int M = strlen(needle);
  
        // haystack is text, needle is pattern
        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N-M; i++)
        {
            int j;
            /* For current index i, check for pattern match */
            for (j = 0; j < M; j++)
            {
                if (haystack[i+j] != needle[j])
                    break;
            }
            if (j == M)  // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
            {
               return i;//printf("Pattern found at index %d \n", i);
            }
        }
        return -1
    }
#endif
#ifdef M3 // O(m*n) time, O(1) space
    int strStr(char *haystack, char *needle) {
        if (!needle) return 0;
        if (!haystack) return -1;
        
        if (!*needle) return 0;
        
        // p1_advance用来保证haystack中待匹配部分的字符串长度是大于等于needle的长度
        // 使用这种技巧可以省去一次对haystack的strlen调用，下面的循环相当于对needle的strlen调用。
        char *p1_advance = haystack;
        for(char *t = needle; *t; ++t) {
            if (!*p1_advance) { // haystack is shorter than needle
                return -1;
            }
            ++p1_advance;
        }
        --p1_advance;
        
        
        char *old_p1 = haystack;
        for(int i=0; *p1_advance; ++i, ++p1_advance) {
            char *p1 = haystack + i;
            char *p2 = needle;
            
            while(*p1 && *p2 && *p1 == *p2) { // *p1 && *p2 对["",""]是很重要的.
                ++p1;
                ++p2;
            }
            
            if(!*p2) {
                return i;
            }
        }
        
        return -1;
    }
#endif
#ifdef M4 // KMP algorithm
/*
refer to :
http://www.geeksforgeeks.org/searching-for-patterns-set-2-kmp-algorithm/
http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html
*/
#endif
};
