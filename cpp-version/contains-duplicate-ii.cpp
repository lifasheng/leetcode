/*
Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array 
such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
*/


#if 0
// my solution
class Solution {
public:
    bool containsNearbyDuplicate(vector<int>& nums, int k) {
        if (nums.empty() || k <=0) return false;
        
        unordered_map<int, int> iimap;
        for (size_t i=0; i<nums.size(); ++i) {
            if (iimap.find(nums[i]) == iimap.end()) {
                iimap[nums[i]] = i;
            } else {
                if (abs(i-iimap[nums[i]]) <= k) {
                    return true;
                } else {
                    iimap[nums[i]] = i;
                }
            }
        }
        
        return false;
    }
};
#endif

#if 1

/*
Explanation: It iterates over the array using a sliding window. The front of the window is at i, the rear of the window is k steps back. 
The elements within that window are maintained using a set. While adding new element to the set, if add() returns false, it means the element already exists in the set. 
At that point, we return true. If the control reaches out of for loop, it means that inner return true never executed, meaning no such duplicate element was found.

exmaple：1，2，4，2，5，3， k=2
只有当i>k时，才有必要从set中删除一个已有元素，当然是删除window中最前面那个元素。
java中的set.add()会返还true或false，c++中则是返还一个pair，pair.second时true或false。

c++ unordered_set::insert:
The function returns a pair object whose first element is an iterator pointing either to the newly inserted element in the container or to the element whose key is equivalent, 
and a bool value indicating whether the element was successfully inserted or not.
*/
class Solution {
public:
    bool containsNearbyDuplicate(vector<int>& nums, int k) {
        unordered_set<int> iset;
        for(int i = 0; i < nums.size(); i++){
            if(i > k) iset.erase(nums[i-k-1]);
            if(!iset.insert(nums[i]).second) return true;
        }
        return false;
    }
};
#endif
