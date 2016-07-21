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


int main() {
    Solution s;
    vector<int> v1 = {1, 2, 2, 1};
    vector<int> v2 = {2, 2};
    vector<int> v = s.intersect(v1, v2);
    for(auto i: v) { cout << i << "  " ;}
    cout << endl;
    return 0;
}
