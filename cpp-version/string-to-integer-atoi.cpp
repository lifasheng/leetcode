class Solution {
public:
    int atoi(const char *str) {
        // handle NULL case
        if (!str) return 0;
        
        const char *p = str;
        int sign = 1;
        long long result = 0;
        
        // skip whitespace
        while(*p && isspace(*p)) {
            ++p;
        }
        
        // handle empty string
        if (!*p) return 0;
        
        // handle sign char
        if (*p == '+') {
            ++p;
        }
        else if (*p == '-') {
            ++p;
            sign = -1;
        }
        
        while(*p) {
            // handle non-digit char
            if (*p < '0' || *p > '9') {
                break;
            }
            
            result = result * 10 + *p - '0';
            
            // handle out of range
            if (sign == 1 && result > INT_MAX) {
                return INT_MAX;
            }
            else if (sign == -1 && result * sign < INT_MIN) {
                return INT_MIN;
            }
            
            ++p;
        }
        
        result *= sign;
        
        return (int)result;
    }

    // new implementation.
    int atoi(string str) {
        const int n = str.size();
        if (n==0) return 0;
        
        const char *p = &str[0];
        // handle space
        while(*p && isspace(*p)) ++p;
        
        if(!*p) return 0;
        
        int sign = 1;
        if (*p == '+') {
            ++p;
        }
        else if (*p == '-') {
            sign=-1; 
            ++p;
        }
        
        // 这里没有使用long long类型，而是通过和INT_MAX INT_MIN进行特殊判断来处理。
        int result = 0;
        while(*p) {
            if (!isdigit(*p)) break;
            
            int d = *p-'0';
            if (sign==1) {
                if(result == INT_MAX/10 && d > INT_MAX%10 || result > INT_MAX/10) {
                    return INT_MAX;
                }
            }
            else {
                if (result == abs(INT_MIN/10) && d > abs(INT_MIN%10) || -1*result < INT_MIN/10) {
                    return INT_MIN;
                }
            }
            
            result = result * 10 + d;
            
            ++p;
        }
        
        return sign * result;
    }
};
