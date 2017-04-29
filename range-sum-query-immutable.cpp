/*
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

Example:
Given nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3
Note:
You may assume that the array does not change.
There are many calls to sumRange function.
*/

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */

// g++ range-sum-query-immutable.cpp  --std=c++0x ; ./a.out 

#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

#define M3

#ifdef M1
class NumArray {
private:
    vector<int> vi;
public:
    NumArray(const vector<int> & nums) : 
	vi(nums.begin(), nums.end()) {
    }
    
    // straightforward method
    // time limitation exceeded
    int sumRange(int i, int j) {
        int result = 0;
        if (i >= 0 && i <= j && j < vi.size()) {
            for (size_t k=i; k<=j && k<vi.size(); ++k) {
                result += vi[k];
            }
        }
        return result;
    }

};
#endif

#ifdef M2
class NumArray {
private:
    size_t size;
    vector<vector<int> > sums;
public:
    NumArray(const vector<int> & nums) : 
	size(nums.size()),
        sums(size, vector<int>(size)) {
            for(size_t i=0; i<size; ++i) {
                sums[i][i] = nums[i];
                for (size_t j=i+1; j<size; ++j) {
                    sums[i][j] = sums[i][j-1] + nums[j];
                }
            }
    }
    
    // memory limitation exceeded
    int sumRange(int i, int j) {
        if (i >= 0 && i <= j && j < size) {
            return sums[i][j];
        }
        return 0;
    }

};
#endif

#ifdef M3
// sumRange[0, i] = sums[0];
// sumRange[0, 1] = sums[1];
// sumRange[0, 2] = sums[2];
// sumRange[0, i] = sums[i];
// sumRange[1, 1] = nums[1] = sums[1]-sums[0];
// sumRange[1, 2] = nums[1]+nums[2] = (nums[0]+nums[1]+nums[2]) - (nums[0])
// sumRange[1, 3] = nums[1]+nums[2]+nums[3] = (nums[0]+nums[1]+nums[2]+nums[3]) - (nums[0])
// sumRange[2, 3] = nums[2]+nums[3] = (nums[0]+nums[1]+nums[2]+nums[3]) - (nums[0]+nums[1])
// sumRange[i, j] = sums[j]- (i>0 ? sums[i-1] : 0);
class NumArray {
private:
    vector<int> sums;
public:
    NumArray(const vector<int> & nums) {
        if (nums.empty()) return;
        
        sums.push_back(nums[0]);
        for(int i=1; i<nums.size(); ++i) {
            sums.push_back(sums[i-1] + nums[i]);
        }
    }
    
    int sumRange(int i, int j) {
        if (i >= 0 && i <= j && j < sums.size()) {
            return sums[j] - (i>0 ? sums[i-1] : 0);
        }
        return 0;
    }
};
#endif

int main() {
    vector<int> vi = {-2, 0, 3, -5, 2, -1};
    NumArray array(vi);
    assert(1 == array.sumRange(0, 2));
    assert(-1 == array.sumRange(2, 5));
    assert(-3 == array.sumRange(0, 5));
}
