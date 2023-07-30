class Solution {
public:
/*
http://www.cnblogs.com/chasuner/p/validNumber.html
用有限状态机，非常简洁，不需要复杂的各种判断！
先枚举一下各种合法的输入情况：
1.空格+ 数字 +空格
2.空格+ 点 + 数字 +空格
3.空格+ 符号 + 数字 +　空格
4.空格 + 符号 + 点 +　数字　＋空格
5.空格 + (1, 2, 3, 4） + e +　(1, 2, 3, 4) +空格
组后合法的字符可以是：
1.数字
2.空格
有限状态机的状态转移过程：
起始为0：
　　当输入空格时，状态仍为0，
　　输入为符号时，状态转为3，3的转换和0是一样的，除了不能再接受符号，故在0的状态的基础上，把接受符号置为-1；
　　当输入为数字时，状态转为1, 状态1的转换在于无法再接受符号，可以接受空格，数字，点，指数；状态1为合法的结束状态；
　　当输入为点时，状态转为2，状态2必须再接受数字，接受其他均为非法；
　　当输入为指数时，非法；
状态1：
　　接受数字时仍转为状态1，
　　接受点时，转为状态4，可以接受空格，数字，指数，状态4为合法的结束状态，
　　接受指数时，转为状态5，可以接受符号，数字，不能再接受点，因为指数必须为整数，而且必须再接受数字；
状态2：
　　接受数字转为状态4；
状态3：
　　和0一样，只是不能接受符号；
状态4：
　　接受空格，合法接受；
　　接受数字，仍为状态4；
　　接受指数，转为状态5，
状态5：
　　接受符号，转为状态6，状态6和状态5一样，只是不能再接受符号，
　　接受数字，转为状态7，状态7只能接受空格或数字；状态7为合法的结束状态；
状态6：
　　只能接受数字，转为状态7；
状态7：
　　接受空格，转为状态8，状态7为合法的结束状态；
　　接受数字，仍为状态7；
状态8：
　　接受空格，转为状态8，状态8为合法的结束状态；
*/
    bool isNumber(const char *s) {
        enum InputType
        {
            INVALID,    // 0
            SPACE,      // 1
            SIGN,       // 2
            DIGIT,      // 3
            DOT,        // 4
            EXPONENT,   // 5
            NUM_INPUTS  // 6
        };
    
        int transitionTable[][NUM_INPUTS] =
        {
            -1,  0,  3,  1,  2, -1,     // next states for state 0
            -1,  8, -1,  1,  4,  5,     // next states for state 1
            -1, -1, -1,  4, -1, -1,     // next states for state 2
            -1, -1, -1,  1,  2, -1,     // next states for state 3
            -1,  8, -1,  4, -1,  5,     // next states for state 4
            -1, -1,  6,  7, -1, -1,     // next states for state 5
            -1, -1, -1,  7, -1, -1,     // next states for state 6
            -1,  8, -1,  7, -1, -1,     // next states for state 7
            -1,  8, -1, -1, -1, -1,     // next states for state 8
        };
    
        int state = 0;
        while (*s != '\0')
        {
            InputType inputType = INVALID;
            if (isspace(*s))
                inputType = SPACE;
            else if (*s == '+' || *s == '-')
                inputType = SIGN;
            else if (isdigit(*s))
                inputType = DIGIT;
            else if (*s == '.')
                inputType = DOT;
            else if (*s == 'e' || *s == 'E')
                inputType = EXPONENT;
            
            // get next state from current state and input symbol
            state = transitionTable[state][inputType];
            
            if (state == -1) // invalid input
                return false;
            else
                ++s;
        }
    
        return state == 1 || state == 4 || state == 7 || state == 8;
    }
};

// my implementation, easy to follow.
class Solution {
public:
/*
test case:
"  "   -> false
"1"    -> true
"1 "   -> true
" .1 " -> true
" 1. " -> true
" 1 ." -> false
" .1. " -> false
" 1e10 " -> true
" 1.2e-5 " -> true
" 1e+6 " -> true
" 8. 4"  -> false
"-1.e49046 " -> true
特别需要注意的是：
1. 小数点可能在数字的前面也可能在后面;
   所以要用一个计数来判断小数点出现了几次，2次就false;
2. 数字后面可以是空格，'\0', 小数点或e
3. 小数点后面可以是空格，'\0'， 数字或e
4. e前面可以有+/-, e后面必须紧接着数字，
   其实处理e方式简单，跳过e本身，跳过+/-，接着跳过数字
*/
    bool isNumber(string s) {
        if (s.empty()) return false;
        
        const char *p = &s[0];
        int dotnum = 0; // 判断小数点出现的次数
        
        // 最前面可以是空格
        while(*p && isspace(*p)) ++p;
        
        // 空串
        if (!*p) return false;
        
        // 加减号
        if (*p == '+' || *p == '-') ++p;
        
        // +/- 后面可以是小数点
        if (*p == '.') {
            ++p;
            ++dotnum;
        }
        
        // +/-/. 后面必须是数字
        if (!isdigit(*p)) return false;
        while(*p && isdigit(*p)) ++p;
        
        // 如果数字后面是空格，则后面必须全是空格，如：  " .1  "
        if (isspace(*p)) {
            while (*p && isspace(*p)) ++p;
            return !*p;
        }
        // 数字后面是'\0', 如：  "+1"
        if(!*p) return true;
        
        // 此时数字后面必须是小数点或者e
        if (*p != '.' && *p != 'e') return false;
        
        if (*p == 'e') { // 数字后面接e "1e-2"
            ++p;
            if (*p == '+' || *p == '-') ++p;
            
            // e后面必须是数字
            if (!*p || !isdigit(*p)) return false;
            while(*p && isdigit(*p)) ++p;
        }
        else { // *p == '.' // 数字后面接.
            // skip .
            ++p;
            ++dotnum;
            if (dotnum >=2) return false;
            
            // 如果小数点后面是空格，则必须全部是空格，如：  " 1.  "
            if (isspace(*p)) {
                while (*p && isspace(*p)) ++p;
                return !*p;
            }
            
            // 小数点后面是'\0'， 如： "1."
            if (!*p) return true;
                
            if (*p == 'e') { // 小数点后面可以接e "1.e+3"
                ++p;
                if (*p == '+' || *p == '-') ++p;
                // e后面必须是数字
                if (!*p || !isdigit(*p)) return false;
                while(*p && isdigit(*p)) ++p;
            }
            else { // 此时.后面必须是数字 " 1.2 ",
                if (!isdigit(*p)) return false;
                while(*p && isdigit(*p)) ++p;
                
                // 此时的数字后面只能接e了： " 1.23e-4 "
                if(*p == 'e') {
                    ++p;
                    if (*p == '+' || *p == '-') ++p;
                    // e后面必须是数字
                    if (!*p || !isdigit(*p)) return false;
                    while(*p && isdigit(*p)) ++p;
                }
            }
        }
        
        // 最后面也可以是空格
        while (*p && isspace(*p)) ++p;
        
        return !*p;
    }
};
