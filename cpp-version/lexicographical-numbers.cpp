/*
Given an integer n, return 1 - n in lexicographical order.
For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
*/


// my solution
/*
n = 245
1   10 100 101 102 103 ... 109   11 110 111 112 ... 119   12 120 121 ... 129   13 130 131 ... 139     14 140 141 ... 149     15 150 151 ... 159     .......   19 190 191 ... 199
2   20 200 201 202 203 ... 209   21 ........

1      2     3   ... 9
10    11   12  13 .. 19 20 ... 29
100 101 ...109    110..119   120..129 130.. 139

we can see that it is a depth first search issue.
we can solve it using recursive process.
*/
class Solution {
public:
    vector<int> lexicalOrder(int n) {
        vector<int> result;
        for (int i=1; i<=9; ++i) {
            helper(n, i, result);
        }
        return result;
    }
    
    void helper(int n, int i, vector<int> &vi) {
        if (i>n) return;
        vi.push_back(i);
        for (int j=i*10; j<=n && j<(i+1)*10; ++j) {
            helper(n, j, vi);
        }
    }
};



/*
The basic idea is to find the next number to add.
Take 45 for example: if the current number is 45, the next one will be 450 (450 == 45 * 10)(if 450 <= n), or 46 (46 == 45 + 1) (if 46 <= n) or 5 (5 == 45 / 10 + 1)(5 is less than 45 so it is for sure less than n).
We should also consider n = 600, and the current number = 499, the next number is 5 because there are all "9"s after "4" in "499" so we should divide 499 by 10 until the last digit is not "9".
It is like a tree, and we are easy to get a sibling, a left most child and the parent of any node.
*/

// loop solution
class Solution {
public:
    vector<int> lexicalOrder(int n) {
        vector<int> res(n);
        int cur = 1;
        for (int i = 0; i < n; i++) {
            res[i] = cur;
            if (cur * 10 <= n) {
                cur *= 10;
            } else {
                if (cur >= n)  //  = is important here! for example, n = 15, when cur == 15, we should move to 2 . acutally we can use (cur == n) here.
                    cur /= 10;
                cur += 1;
                while (cur % 10 == 0) // for example, n = 215, when cur = 199, we should move to 2
                    cur /= 10;
            }
        }
        return res;
    }
};


// Java solution
public List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>(n);
        int curr = 1;
        for (int i = 1; i <= n; i++) {
            list.add(curr);
            if (curr * 10 <= n) {
                curr *= 10;
            } else if (curr % 10 != 9 && curr + 1 <= n) {
                curr++;
            } else {
                while ((curr / 10) % 10 == 9) {
                    curr /= 10;
                }
                curr = curr / 10 + 1;
            }
        }
        return list;
    }
