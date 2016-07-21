#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include<stdio.h>
using namespace std;


class Solution {
public:
    vector<int> intersection(vector<int>& nums1, vector<int>& nums2) {
        vector<int> result;
        std::unordered_map<int, int> mi;
#if 0
        for(auto iter=nums1.begin(); iter!=nums1.end(); ++iter) {
            mi.emplace(*iter, 1);
        }
        for(auto iter=nums2.begin(); iter!=nums2.end(); ++iter) {
            if (mi.count(*iter)) {
                mi[*iter] = 0;
            }
        }
        for(auto iter=mi.begin(); iter!=mi.end(); ++iter) {
            if (!iter->second) {
                result.push_back(iter->first);
            }
        }
#else
        for(auto i: nums1) {
            mi.emplace(i, 1);
        }
        for(auto i: nums2) {
            if (mi.count(i)) {
                mi[i] = 0;
            }
        }
        for(auto i: mi) {
            if (!i.second) {
                result.push_back(i.first);
            }
        }
#endif
        return result;
    }
};


int main() {
    Solution s;
    vector<int> v1 = {1, 2};
    vector<int> v2 = {1, 1};
    vector<int> v = s.intersection(v1, v2);
    for(auto i: v) { cout << i << "  " ;}
    cout << endl;
    return 0;
}
