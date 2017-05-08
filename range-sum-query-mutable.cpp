/*
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

The update(i, val) function modifies nums by updating the element at index i to val.
Example:
Given nums = [1, 3, 5]

sumRange(0, 2) -> 9
update(1, 2)
sumRange(0, 2) -> 8
Note:
The array is only modifiable by the update function.
You may assume the number of calls to update and sumRange function is distributed evenly.
*/

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */


// g++ range-sum-query-mutable.cpp  --std=c++0x ; ./a.out

#include <iostream>
#include <cmath>
#include <cassert>
#include <vector>
using namespace std;

#define Array_Implementation

#ifdef Tree_Implementation
struct SegmentTreeNode {
    int sum;
    int rangeBegin, rangeEnd;
    SegmentTreeNode *left, *right;
    SegmentTreeNode(int s, int beg, int end, SegmentTreeNode *l=NULL, SegmentTreeNode *r=NULL) {
        sum = s;
        rangeBegin = beg;
        rangeEnd = end;
        left = l;
        right = r;
    }
};

class NumArray {
private:
    vector<int> nums;
    SegmentTreeNode *root;
private:
    int getMid(int beg, int end) {
        return beg + (end-beg)/2;
    }
    SegmentTreeNode* constructTree(int beg, int end) {
        if (beg == end) {
            return new SegmentTreeNode(nums[beg], beg, end);
        }
        int mid = getMid(beg, end);
        SegmentTreeNode *left = constructTree(beg, mid);
        SegmentTreeNode *right = constructTree(mid+1, end);
        return new SegmentTreeNode(left->sum+right->sum, beg, end, left, right);
    }
    
    int getSum(SegmentTreeNode *node, int qs, int qe) {
        if (node == NULL) return 0;
        int beg = node->rangeBegin;
        int end = node->rangeEnd;
        if (qs <= beg && qe >= end) {
            return node->sum;
        }
        
        if (qs > end || qe < beg) {
            return 0;
        }
        
        return getSum(node->left, qs, qe) + getSum(node->right, qs, qe);
    }
    
    void updateValue(SegmentTreeNode *node, int index, int diff) {
        if (node == NULL) return; // !!! important for leave node
        int beg = node->rangeBegin;
        int end = node->rangeEnd;
        if (beg <= index && index <= end) {
            node->sum += diff;
            updateValue(node->left, index, diff);
            updateValue(node->right, index, diff);
        }
    }
    
public:
    NumArray(const vector<int> &vi): nums(vi), root(NULL) {
        if (vi.empty()) return;
        root = constructTree(0, nums.size()-1);
    }
    
    void update(int i, int val) {
        if (i<0 || i>nums.size()) return;
        int diff = val - nums[i];
        nums[i] = val; // !!! important
        updateValue(root, i, diff);
    }
    
    int sumRange(int i, int j) {
        if (i<0 || j>nums.size() || i>j) {
            return 0;
        }
        
        return getSum(root, i, j);
    }
};

#endif


#ifdef Array_Implementation
class NumArray {
private:
    vector<int> nums;
    vector<int> seg;
private:
    int getMid(int beg, int end) {
        return beg + (end-beg)/2;
    }
    int constructTree(int beg, int end, int si) {
        if (beg == end) {
            seg[si] = nums[beg];
        } else {
            int mid = getMid(beg, end);
            seg[si] = constructTree(beg, mid, 2*si+1) + constructTree(mid+1, end, 2*si+2);
        }
        return seg[si];
    }
    
    int getSum(int beg, int end, int si, int qs, int qe) {
        if (qs <= beg && qe >= end) {
            return seg[si];
        }
        
        if (qs > end || qe < beg) {
            return 0;
        }
        
        int mid = getMid(beg, end);
        return getSum(beg, mid, 2*si+1, qs, qe) + getSum(mid+1, end, 2*si+2, qs, qe);
    }
    
    void updateValue(int beg, int end, int si, int index, int diff) {
        if (beg <= index && index <= end) {
	    //cout << "beg: " << beg << ", end: " << end << ", si: " << si << endl;
            seg[si] += diff;
            if (beg == end) return; // !!! important, we reach the leave node here.

            int mid = getMid(beg, end);
            updateValue(beg, mid, 2*si+1, index, diff);
            updateValue(mid+1, end, 2*si+2, index, diff);
        }
    }
    
    void dumpSegTree() {
        for(auto i:seg) { cout << i << " "; }
	cout << endl;
    }
    
public:
    NumArray(const vector<int> &vi): nums(vi) {
        if (vi.empty()) return;
        size_t n = vi.size();
        int h = (int)ceil(log2(n));
        int segTreeSize = pow(2, h+1)-1;
        seg.resize(segTreeSize);
        constructTree(0, n-1, 0);
        //dumpSegTree();
    }
    
    void update(int i, int val) {
        if (i<0 || i>=nums.size()) return;
        int diff = val - nums[i];
        nums[i] = val; // !!! important
        updateValue(0, nums.size()-1, 0, i, diff);
        //dumpSegTree();
    }
    
    int sumRange(int i, int j) {
        if (i<0 || j>=nums.size() || i>j) {
            return 0;
        }
        
        return getSum(0, nums.size()-1, 0, i, j);
    }
};
#endif

int main() {
    if (0)
    {
        vector<int> vi = {1,3,5};
        NumArray array(vi);
        assert(9 == array.sumRange(0, 2));
        array.update(1, 2);
        assert(8 == array.sumRange(0, 2));
    }
    {
        vector<int> vi = {7,2,7, 2, 0};
        NumArray array(vi);
        array.update(4, 6);
        array.update(0, 2);
        array.update(0, 9);
        assert(6 == array.sumRange(4, 4));
        array.update(0, 4);
        assert(13 == array.sumRange(0, 2));
    }
}
