class Solution {

public:

    bool isPalindrome(string s) {

        //if (s.size() == 0) return true;

        

        //这里一定要加::全局作用域符号

        transform(s.begin(), s.end(), s.begin(), ::tolower);

        

        typedef string::iterator Iterator;

        Iterator left = s.begin(), right = prev(s.end());

        

        // 注意区别isalpha（字符）， isdigit（数字）， isalnum（字符和数字）

        while(left < right) {

            if (!::isalnum(*left)) ++left;

            else if (!isalnum(*right)) --right;

            else if (*left != *right) return false;

            else {

                ++left;

                --right;

            }

        }

        

        return true;

    }

};
