#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include<stdio.h>
using namespace std;


/*
 *  Given two arrays, write a function to compute their intersection.
 *
 *  Example:
 *  Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 *
 *  Note:
 *
 *      Each element in the result should appear as many times as it shows in both arrays.
 *      The result can be in any order.
 *
 *          Follow up:
 *
 *              What if the given array is already sorted? How would you optimize your algorithm?
 *              What if nums1's size is small compared to nums2's size? Which algorithm is better?
 *              What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
 *
*/

#define USE_SORTING

#ifdef USE_MAP
class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        vector<int> result;
        std::unordered_map<int, int> mi;
        for(auto i: nums1) {
            mi[i]++;
        }
        for(auto i: nums2) {
            if (mi[i]) {
                mi[i]--;
                result.push_back(i);
            }
        }
        return result;
    }
};
#endif

#ifdef USE_SORTING
class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        vector<int> result;
        sort(nums1.begin(), nums1.end());
        sort(nums2.begin(), nums2.end());
        auto iter1 = nums1.begin();
        auto iter2 = nums2.begin();
        while(iter1 != nums1.end() && iter2 != nums2.end()) {
            if (*iter1 < *iter2) {
                ++iter1;
            }
            else if (*iter1 > *iter2) {
                ++iter2;
            }
            else {
                result.push_back(*iter1);
                // do not forget to move forward for both
                ++iter1;
                ++iter2;
            }
        }

        return result;
    }
};
#endif


int main() {
    Solution s;
    vector<int> v1 = {1, 2, 2, 1};
    vector<int> v2 = {2, 2};
    vector<int> v = s.intersect(v1, v2);
    for(auto i: v) { cout << i << "  " ;}
    cout << endl;
    return 0;
}
