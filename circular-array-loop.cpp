/*
You are given an array of positive and negative integers. If a number n at an index is positive, then move forward n steps. Conversely, if it's negative (-n), move backward n steps. Assume the first element of the array is forward next to the last element, and the last element is backward next to the first element. Determine if there is a loop in this array. A loop starts and ends at a particular index with more than 1 element along the loop. The loop must be "forward" or "backward'.

Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.

Example 2: Given the array [-1, 2], there is no loop.

Note: The given array is guaranteed to contain no element "0".

Can you do it in O(n) time complexity and O(1) space complexity?

*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include <stdio.h>
#include <sstream>
using namespace std;

/*
basic idea:
go through the list one by one,
if we find a loop starting from certain postion, then return.
else, try next postion.
*/

class Solution {
private:
    // make sure the index i in valid ( in range [0, n) )
    int circleRoundRight(int i, int n) {
        int newI = i >= n ? i-n : i;
        if (i < n)
            return newI;
        else
            return circleRoundRight(newI, n);
    }
    int circleRoundLeft(int i, int n) {
        int newI = i < 0 ? n+i : i;
        if (i >= 0)
            return newI;
        else
            return circleRoundLeft(newI, n);
    }
    bool detectLoop(vector<int>& nums, int i) {
        const bool isForward = nums[i] > 0;
        const int size = nums.size();
        int fast = i;
        int slow = i;
        if (isForward) { // from left to right
            // when we are processing index i, it means, we don't detect loop for its previous positions.
            // so if there is loop including index i, then it should start from i, or after index i.
            while(fast >= i && slow >= i) {
                if (nums[fast] < 0) return false; // not same direction
                fast += nums[fast];
                fast = circleRoundRight(fast, size);
                if (nums[fast] < 0) return false;
                fast += nums[fast];
                fast = circleRoundRight(fast, size);

                if (nums[slow] < 0) return false;
                slow += nums[slow];
                slow = circleRoundRight(slow, size);

                // we find a circle, but we need to ensure there is more than one element in this circle.
                if (fast == slow) {
                    slow += nums[slow];
                    slow = circleRoundRight(slow, size);
                    return fast != slow;
                }
            }
        } else { // from right to left
            // when we are processing index i, it means, we don't detect loop for its previous positions.
            // so if there is loop including index i, then it should start from i (backforward), 
            // or after index i (no matter it is forward or backward).
            while(fast <= i && slow <= i) {
                if (nums[fast] > 0) return false;
                fast += nums[fast];
                fast = circleRoundLeft(fast, size);

                if (nums[fast] > 0) return false;
                fast += nums[fast];
                fast = circleRoundLeft(fast, size);

                if (nums[slow] > 0) return false;
                slow += nums[slow];
                slow = circleRoundLeft(slow, size);

                // we find a circle, but we need to ensure there is more than one element in this circle.
                if (fast == slow) {
                    slow += nums[slow];
                    slow = circleRoundLeft(slow, size);
                    return fast != slow;
                }
            }
        }
        return false;
    }
public:
    bool circularArrayLoop(vector<int>& nums) {
        for(int i=0; i<nums.size(); ++i) {
            bool isLoop = detectLoop(nums, i);
            //cout << "i:" << i << ", isLoop:" << isLoop << endl;
            if (isLoop) return true;
        }
        return false;
    }
};

int main() {
    Solution s;
    vector<int> nums = {-1, 2}; // false
    cout << s.circularArrayLoop(nums) << endl;

    vector<int> nums2 = {2, -1, 1, 2, 2}; // true
    cout << s.circularArrayLoop(nums2) << endl;

    vector<int> nums3 = {-2, -1, 1, -2, -2};
    cout << s.circularArrayLoop(nums3) << endl; // true

    vector<int> nums4 = {-1, -2, -3, -4, -5}; // false
    cout << s.circularArrayLoop(nums4) << endl;

    vector<int> nums5 = {1, 2, 1, 1}; // false
    cout << s.circularArrayLoop(nums5) << endl;
 
    return 0;
}


