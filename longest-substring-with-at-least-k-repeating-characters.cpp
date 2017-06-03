/*
Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.

Example 1:

Input:
s = "aaabb", k = 3

Output:
3

The longest substring is "aaa", as 'a' is repeated 3 times.
Example 2:

Input:
s = "ababbc", k = 2

Output:
5

The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
*/

#include<vector>
#include<list>
#include<string>
#include<iostream>
#include<unordered_set>
#include<unordered_map>
using namespace std;


// my solution
/*
two pointer: [start, end)
we just need to judge if the substring [start, end) satisfy "each char appears at least k times".
In order to accelerate the judgement, we use two data structure:
map to record the appearance times for each char.
set to record if any char in the substring appears less than k times

Note that it is important to use a set here, otherwise, we need to go through the map.

consider example: a a b a b, k = 2
when j = 3, m[a] == 3 > k, if we do not have a set, then we must go through the map.
But actually m[b] == 1 < k, so it will definitely fail the check.
If we insert b to the set, then we can quickly know that the substring does not satisfy the rule: "each char appears at least k times". 

so basically, we use set to avoid below check method:

    bool check(const unordered_map<char, int> &m, int k) {
        for (auto entry : m) {
            if (entry.second < k) {
                return false;
            }
        }
        return true;
    }

*/
class Solution {
public:
    int longestSubstring(const string &str, int k) {
        int n = 0;
        int len = str.size();

        for (int i=0; i<len; ++i) {
            //if (len-i <= n) break;
            
            // it is important here!!!
            // i.e.    abbbbbbbbbbbc, k=3
            if (i > 0 && str[i] == str[i-1]) {  
                continue;
            }
            
            unordered_map<char, int> m;
            unordered_set<char> unsatisfied;
            for (int j=i; j<len; ++j) {
                m[str[j]]++;
                if (m[str[j]] >= k) {
                    if (unsatisfied.count(str[j]) > 0) {
                        unsatisfied.erase(str[j]);
                    }
                    if (unsatisfied.empty()) {
                        int sublen = j-i+1;
                        if (sublen > n) {
                            n = sublen;
                        }
                    }
                } else {
                    unsatisfied.insert(str[j]);
                }
            }
        }
        
        return n;
    }
};

int main() {
    Solution solution;
    cout << solution.longestSubstring("aaabb", 3) << endl;
    cout << solution.longestSubstring("", 1) << endl;
    cout << solution.longestSubstring("abcdedghijklmnopqrstuvwxyz", 1) << endl;
    cout << solution.longestSubstring("abbbbbbbbbbc", 3) << endl;
}
