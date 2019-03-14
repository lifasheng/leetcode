/*
169. Majority Element
Easy

Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2
*/


class Solution {
public:
    #if 0
    int majorityElement(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        return nums[ (nums.size()-1)/2 ];
    }
    #endif
    
    #if 0
    int majorityElement(vector<int>& nums) {
        unordered_map<int, int> m;
        int times = 0;
        int majority;
        for (auto i : nums) {
            m[i] ++;
            if (m[i] > times) {
                times = m[i];
                majority = i;
            }
        }
        return majority;
    }
    #endif
    
    #if 1
    /*
        Boyer-Moore算法
        基本思想：
        比较直观的解释：在数组中找到两个不相同的元素并删除它们，不断重复此过程，直到数组中元素都相同，那么剩下的元素就是主要元素。

        先随意确定一个候选元素，count是候选元素的计数，当遇到一个跟候选元素不同的元素时，两者数量上抵消一个，count减1。一旦count变成0，就重新找一个候选元素。
        当遇到一个与候选元素不同的元素时，就要抵消。对于候选元素和当前元素，可能存在两种情况：1）两者中有一个正好是主要元素；2）两者都不是主要元素。
        对于情况1)，抵消过后，主要元素还是主要元素；对于情况2），可以说主要的元素的地位得到了巩固。所以算法最终能找到主要元素。
        
        One More Thing
        上面的题目指出，满足条件的元素一定存在，那就可以直接返回我们找到的元素了。但事实上有时候这样的元素不一定存在，那么当我们找到这样一个元素时，还要进一步验证一下它是否满足条件。
        很简单，再遍历一遍，统计它的出现次数。

        作者：曾会玩
        链接：https://www.jianshu.com/p/dfd676b71ef0
        来源：简书
        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
    */
    int majorityElement(vector<int>& nums) {
        int count = 0;
        int majority;
        for (auto i : nums) {
            if (count == 0) {
                majority = i;
            }
            
            if (i == majority) {
                ++ count;
            } else {
                -- count;
            }
        }
        return majority;
    }
    #endif
};

