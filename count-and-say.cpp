class Solution {
public:
    string getNext(string s) {
        int size = s.size();
        stringstream out;
        for(int i=0; i<size;) {
            int j;
            for(j = i; j<size; ++j) {
                if (s[j] != s[i]) {
                    break;
                }
            }
            out << j-i << s[i];
            i = j;
        }
        
        return out.str();
    }
    
    string countAndSay(int n) {
        string s = "1";
        
        while(--n) {
            s = getNext(s);
        }
        
        return s;
    }
};
