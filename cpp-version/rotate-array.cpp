/*

189. Rotate Array

Rotate an array of n elements to the right by k steps.

For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].

Note:
Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.

[show hint]

Related problem: Reverse Words in a String II
*/

1234 567
first reverse two sub array, then reverse the whole array.
=> 4321  765
=> 5671234


class Solution {
public:
    void rotate(vector<int>& nums, int k) {
        size_t n = nums.size();
        if (n<1) return;
        if (k>=n) k %= n;
        if (k==0) return;
        
        reverse(&nums[0], &nums[n-k-1]);
        reverse(&nums[n-k], &nums[n-1]);
        reverse(&nums[0], &nums[n-1]);
    }
    
    void reverse(auto beg, auto end) {
        while(beg < end) {
            std::swap(*beg, *end);
            ++beg;
            --end;
        }
    }
};
