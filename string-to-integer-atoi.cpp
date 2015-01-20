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
};
