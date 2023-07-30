/*
Given an array of integers, find if the array contains any duplicates. 
Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.
*/

// my solution
class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        unordered_set<int> si;
        for (auto i : nums) {
            if (si.count(i) > 0) return true;
            si.insert(i);
        }
        return false;
    }
};





/*
Using anonymous set<>.
Not the most efficient as many already pointed out... but if you like one-liners ;) 
akin to the solution possible with python.
*/

#include <set>
using namespace std;
class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        return nums.size() > set<int>(nums.begin(), nums.end()).size();        
    }
};
