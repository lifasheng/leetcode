/*
Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot be planted in adjacent plots - they would compete for water and both would die.

Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.

Example 1:
Input: flowerbed = [1,0,0,0,1], n = 1
Output: True
Example 2:
Input: flowerbed = [1,0,0,0,1], n = 2
Output: False
Note:
The input array won't violate no-adjacent-flowers rule.
The input array size is in the range of [1, 20000].
n is a non-negative integer which won't exceed the input array size.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include<stdio.h>
using namespace std;

// idea: go through the list, try to change 0 to 1 if possible, then count how many postions did we change.

class Solution {
private:
    bool isNeighborOne(vector<int>& flowerbed, int i) {
        return (i-1 >=0 && flowerbed[i-1] == 1 ) 
                || (i+1<flowerbed.size() && flowerbed[i+1] == 1);
    }
public:
    bool canPlaceFlowers(vector<int>& flowerbed, int n) {
        int m = 0;
        for (int i=0; i<flowerbed.size(); ++i) {
            if (flowerbed[i] == 0) {
                if (! isNeighborOne(flowerbed, i) ) {
                    flowerbed[i] = 1; // assume we change this slot from 0 to 1.
                    ++m;
                }
            }
        }

        return n <= m;
    }
};

int main() {
    Solution s;
    vector<int> f1 = {0, 1, 0};
    vector<int> f2 = {1, 0, 0};
    vector<int> f3 = {0, 1, 0, 0};
    vector<int> f4 = {0};
    vector<int> f5 = {0, 0, 1, 0, 0};
    cout <<s.canPlaceFlowers(f1, 1)<<endl;
    cout <<s.canPlaceFlowers(f2, 1)<<endl;
    cout <<s.canPlaceFlowers(f3, 1)<<endl;
    cout <<s.canPlaceFlowers(f4, 1)<<endl;
    cout <<s.canPlaceFlowers(f5, 2)<<endl;

    return 0;
}


