class Solution {
public:
    vector<int> plusOne(vector<int> &digits) {
        add(digits, 1);
        return digits;
    }
    
    // 0 <= digit <= 9
    void add(vector<int> &digits, int digit) {
        int size = digits.size();
        int c = digit; // carry
        for(int i=size-1; i>=0; --i) {
            digits[i] += c;
            c = digits[i] / 10;
            digits[i] %= 10;
        }
        
        if (c == 1) {
            digits.insert(digits.begin(), 1);
        }
    }
};
