class Solution {
public:
    string multiply(string num1, string num2) {
        if (num1 == "0" || num2 == "0") return "0";
        
        const int m = num1.size();
        const int n = num2.size();
        int k = m+n;
        int carry=0;
        // 乘积最大长度为m+n+1
        vector<int> f(m+n+1, 0);
        
        // 按照手工乘法的步骤，进行计算。
        for(int j=n-1; j>=0; --j) { // 对num2的每个数字
            int index = k--; // 错一位
            carry = 0; //只对num2[j]的乘积进行进位
            for(int i=m-1; i>=0; --i) { // 对num1的每个数
                int t = (num2[j]-'0') * (num1[i]-'0');
                t += carry;
                f[index] += t%10;
                carry = t/10;
                index--;
            }
            if (carry) {
                f[index] = carry;
            }
        }
        
        // "123"x"456", f=[0, 0, 4, 15, 10, 8, 8]
        carry = 0;
        for(int i=f.size()-1; i>=0; --i) {
            f[i] += carry;
            carry = f[i]/10;
            f[i] %= 10;
        }
        assert(carry == 0);
        
        // "123"x"456", f=[0, 0, 5, 6, 0, 8, 8], 最终结果需要去掉开头的0
        string result;
        int start = 0;
        for(int i=0;i<f.size(); ++i) {
            if (f[i]) {
                start = i;
                break;
            }
        }
        for(int j=start; j<f.size(); ++j) {
            result += f[j]+'0';
        }
        
        return result;
    }
};
