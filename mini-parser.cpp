/*
Given a nested list of integers represented as a string, implement a parser to deserialize it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Note: You may assume that the string is well-formed:

String is non-empty.
String does not contain white spaces.
String contains only digits 0-9, [, - ,, ].
Example 1:

Given s = "324",

You should return a NestedInteger object which contains a single integer 324.
Example 2:

Given s = "[123,[456,[789]]]",

Return a NestedInteger object containing a nested list with 2 elements:

1. An integer containing value 123.
2. A nested list containing two elements:
    i.  An integer containing value 456.
    ii. A nested list with one element:
         a. An integer containing value 789.
*/

#include<vector>
#include<cassert>
#include<stack>
#include<string>
#include<iostream>
using namespace std;



/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * class NestedInteger {
 *   public:
 *     // Constructor initializes an empty nested list.
 *     NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     NestedInteger(int value);
 *
 *     // Return true if this NestedInteger holds a single integer, rather than a nested list.
 *     bool isInteger() const;
 *
 *     // Return the single integer that this NestedInteger holds, if it holds a single integer
 *     // The result is undefined if this NestedInteger holds a nested list
 *     int getInteger() const;
 *
 *     // Set this NestedInteger to hold a single integer.
 *     void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     void add(const NestedInteger &ni);
 *
 *     // Return the nested list that this NestedInteger holds, if it holds a nested list
 *     // The result is undefined if this NestedInteger holds a single integer
 *     const vector<NestedInteger> &getList() const;
 * };
 */

class NestedInteger {
private:
    int val;
    vector<NestedInteger> vni; // note: incomplete type container!!!
    bool isInt;
public:
    NestedInteger() : isInt(false) {
    }
    NestedInteger(int value): val(value), isInt(true) {
    }
    bool isInteger() const {
        return isInt;
    }
    int getInteger() const {
        return val;
    }

    void setInteger(int value) {
        isInt = true;
        val = value;
    }

    void add(const NestedInteger &ni) {
        isInt= false;
        vni.push_back(ni);
    }

    const vector<NestedInteger> &getList() const {
        return vni;
    }
};

ostream& operator<<(ostream& out, const NestedInteger &ni)
{
    if (ni.isInteger()) {
        out << ni.getInteger() ;
    } else {
        vector<NestedInteger> vni = ni.getList();
        cout << "[";
        if (!vni.empty()) {
            for (int i=0; i< vni.size()-1; ++i) {
            cout << vni[i] << ",";
        }
            cout << vni[vni.size()-1];
        }
        cout << "]";
    }
    return out;
}

 // test case
 /*
 [[1,2],[3,4]]
 [[[1],2],3,4]
 [1,2,3]
 [[1,2],3]
 [1,[2,3]]
 []
 [[]]
 [[],[]]

 思路：
 类似于表达式求值，我们可以用两个堆栈来实现。
 将逗号看成和加减乘除类似的运算符，只不过是我们遇到右括号时，必须一次性把同一个括号内的逗号运算符都得弹栈，再把左括号弹栈。
 而在计算逗号运算时，需要保证先入栈的在前面，这又要用一个临时栈来辅助。
 */
class Solution {
public:
    NestedInteger deserialize(string s) {
        stack<char> opsStk;
        stack<NestedInteger> niStk;
        int num = 0;
        bool isNeg = false;
        for (int i=0; i<s.size(); ++i) {
            char c = s[i];
            if (c == '-') {
                isNeg = true;
            }  else if(isdigit(c)) { // 0-9
                num = num*10 + c-'0';
                int j=i+1;
                for (; j<s.size(); ++j) {
                    if (isdigit(s[j])) {
                         num = num*10 + s[j]-'0';
                    } else {
                        break;
                    }
                }
                i=j-1; // because it will ++i in the for loop

                if (isNeg) {
                    isNeg = false;
                    num *= -1;
                }

                NestedInteger ni(num);
                niStk.push(ni);
                num = 0;

            } else { // no digit
                if (c == '[' || c == ',') {
                    opsStk.push(c);
                } else if (c == ']') {
                    if (opsStk.top() == '[') {
                        opsStk.pop(); // pop '['
                        NestedInteger ni;
                        if (s[i-1] != '[') { // in [] case, no need to pop
                            NestedInteger ni1 = niStk.top(); niStk.pop();
                            ni.add(ni1);
                        }
                        niStk.push(ni);
                    } else { // ','
                        stack<NestedInteger> tmpStk;
                        opsStk.pop(); // pop ','
                        NestedInteger ni1 = niStk.top(); niStk.pop();
                        NestedInteger ni2 = niStk.top(); niStk.pop();
                        tmpStk.push(ni1);
                        tmpStk.push(ni2);
                        while (opsStk.top() != '[') {
                            opsStk.pop(); // pop ','
                            NestedInteger ni3 = niStk.top(); niStk.pop();
                            tmpStk.push(ni3);
                        }
                        // now opsStk.top() == '['
                        opsStk.pop(); // pop '['
                        NestedInteger ni;
                        while(!tmpStk.empty()) {
                            NestedInteger ni4 = tmpStk.top(); tmpStk.pop();
                            ni.add(ni4);
                        }
                        niStk.push(ni);
                    }
                } else {
                    assert(false);
                }
            }
        }

        return niStk.top();
        
    }
};

int main() {
    Solution solution;
    cout << solution.deserialize("[-1,-2]") << endl;
    cout << solution.deserialize("[1,2,3]") << endl;
    cout << solution.deserialize("[]") << endl;
    cout << solution.deserialize("[1,[2]]") << endl;
    cout << solution.deserialize("[[],[]]") << endl;
}
