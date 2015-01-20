class Solution {
public:
    string addBinary(string a, string b) {
        string result;
        
        reverse(a.begin(), a.end());
        reverse(b.begin(), b.end());
        
        int carry = 0;
        typedef string::iterator Iterator;
        Iterator pa = a.begin(), pb = b.begin();
        while(pa != a.end() || pb != b.end()) {
            int ia = *pa ? *pa - '0' : 0;
            int ib = *pb ? *pb - '0' : 0;
            int sum = ia + ib + carry;
            result += sum % 2 + '0';
            carry = sum / 2;
            
            if (pa != a.end()) ++pa;
            if (pb != b.end()) ++pb;
        }
        if (carry) {
            result += '1';
        }
        reverse(result.begin(), result.end());
        
        return result;
    }
};
