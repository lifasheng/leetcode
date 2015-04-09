/**
 * Definition for an interval.
 * struct Interval {
 *     int start;
 *     int end;
 *     Interval() : start(0), end(0) {}
 *     Interval(int s, int e) : start(s), end(e) {}
 * };
 */
class Solution {
public:
#define M2
    
#ifdef M1
// O(n) time, O(1) space, TLE超时
    vector<Interval> insert(vector<Interval> &intervals, Interval newInterval) {
        vector<Interval>::iterator iter = intervals.begin();
        while(iter != intervals.end()) {
            if (newInterval.end < iter->start) {
                intervals.insert(iter, newInterval);
                return intervals;
            }
            else if (iter->end < newInterval.start) {
                ++iter;
            }
            else {
                newInterval.start = min(newInterval.start, iter->start);
                newInterval.end   = max(newInterval.end, iter->end);
                iter = intervals.erase(iter); // erase will return iterator of next item before iter is removed.
            }
        }
        intervals.insert(intervals.end(), newInterval);
        return intervals;
    }
#endif
#ifdef M2
/*
利用intervals已经排序的特点，采用二分查找，可以在O(lgn)时间内找到起点和终点。
这里的最坏情况还是O(n)，即要insert的Interval跨头和尾的位置。
但多数情况是2*O(lgn),而解法M1总是O(n)。
binarySearch:
// 二分查找的变形，将intervals中所有的元素展开（也就是说不分start，end），可以看成一个排序数组。
// low，mid，high和经典二分查找类似;
// mid为在展开的数组中的下标，而idx为该元素在intervals数组的下标;
// mid对应的是intervals[idx]的start还是end呢？ 如果mid-2*idx == 1, 则是end，==0,则是start。
// 这个函数有点类似于lower_bound， 它的返回值也可以参考lower_bound来理解。
[ [1,2] ], 0: 返回0
[ [1,5] ], 3: 返回0
[ [3,6] ], 7: 返回1
[ [1,2] [5,8] ], 0/1/2: 返回0
[ [1,2] [5,8] ], 3/4/5/6/7/8: 返回1
[ [1,2] [5,8] ], 9: 返回2
*/
    int binarySearch(vector<Interval> &intervals, int val) {
        int low = 0, high = intervals.size()*2-1;
        while(low <= high) {
            int mid = (low+high)/2; // index in all numbers
            int idx = mid/2; // index in intervals array
            int midv;
            Interval midItem= intervals[idx];
            
            //mid -2*idx == 1 or 0
            midv = (mid-2*idx) ? midItem.end : midItem.start;
            
            if (midv == val) {
                return idx;
            }
            else if (midv < val) {
                low = mid+1;
            }
            else {
                high = mid-1;
            }
        }
        
        return low/2;
    }
    vector<Interval> insert(vector<Interval> &intervals, Interval newInterval) {
        int pos1 = binarySearch(intervals, newInterval.start);
        int pos2 = binarySearch(intervals, newInterval.end);
        
        // newInterval.start is bigger than all numbers in intervals or intervals is empty
        // [], [1,2] => [ [1,2] ]
        // [ [1,2] ], [3, 4] => [ [1,2] [3,4] ]
        if (pos1 == intervals.size()) {
            intervals.push_back(newInterval);
            return intervals;
        }
        // if newInterval.end is bigger than all numbers in interval, then it will merge with last item.
        // [ [1,3] ], [2, 5] => [ [1,5] ]
        if (pos2 == intervals.size()) {
            pos2 = intervals.size()-1;
        }
        
        // 对于newInterval.start，有两种情况： 
        // newInterval.start < intervals[pos1].start
        // newInterval.start >= intervals[pos1].start
        // 不管哪种情况，都需要将newInterval和intervals[pos1]合并。
        newInterval.start = min(newInterval.start, intervals[pos1].start);
        
        // 对于newInterval.end, 同样有两情况：
        // newInterval.end < intervals[pos2].start
        // newInterval.end >= intervals[pos2].start 
        // 对于第一种情况，我们需要将newInterval和intervals[pos2-1]合并。此时注意要保证pos2-1>=0
        // 对于第二种情况，我们将newInterval和intervals[pos2]合并即;
        if (newInterval.end < intervals[pos2].start) {
            if (pos2-1 >= 0) {
                newInterval.end = max(newInterval.end, intervals[pos2-1].end);
            }
            pos2--;
        }
        else {
            newInterval.end = max(newInterval.end, intervals[pos2].end);
        }
        
        // [ [3,5] ], [1,2] => [ [1,2] [3,5] ], pos1=0, pos2=0-1=-1
        // [ [3,5] [8,9] ], [6,7] => [ [3,5] [6,7] [8,9] ], pos1=1, pos2=1-1=0
        if (pos1 > pos2) {
            intervals.insert(next(intervals.begin(), pos1), newInterval);
        }
        else {
            // [ [2,5] ], [3,4] => [ [2,5] ], pos1=0, pos2=0
            // [ [1,4] [6,8] ], [2,5] => [ [1,5] [6,8] ], pos1=0, pos2=1-1=0
            // [ [1,4] [6,8] ], [2,7] => [ [1,8] ], pos1=0, pos2=1
            typedef vector<Interval>::iterator Iterator;
            Iterator iter1 = next(intervals.begin(), pos1);
            Iterator iter2 = next(intervals.begin(), pos2+1); //[first, last), 所以pos2+1,不然pos2那个位置不会被erase。
            
            iter1 = intervals.erase(iter1, iter2); // 返回iter2之后的位置，这里返回的iter1已经不等于iter2了，因为iter2已经失效了。
            intervals.insert(iter1, newInterval);
        }
        return intervals;
    }
#endif
};
class Solution {
public:
/*
如果用网上的代码，最好的情况是直接插入，不用合并，O(n)，
最坏的情况是待插入的Interval横跨所有的Intervals，O(n2)，因为erase时需要拷贝数组元素，所以会超时。
原来自己也写过一个用二分查找的方法，但现在觉得那个代码要判断的条件太多了。
这种代码自己平时有时间是可以写出来的，但是需要一些调试才能保证正确性。
于是再次思考，觉得应该有更好的写法，因此有了以下代码。
这个代码思路清晰，简洁，不容易出错，给别人解释也比较容易。
空间复杂度： 这种实现需要O(n)的空间。
时间复杂度：
构造vector<int>， O(n)
binary search： O(logn);
insert: 最坏是在头部插入O(n)，最好是在尾部插入，O(1)，平均是O(n);
distance: O(1), 注意insert时如果用list插入效率高，但是distance就得O(n)了;
构造vector<Interval> O(n)
所以最坏情况也是O(n)的时间复杂度。
原理：
1. 先用一个int数组保存Interval中的数，由于已经排序，所以可以用binary search。
   理解STL中的lower_bound很重要，经典代码不多说。
2. 找到合适的插入位置之后，分别插入newInterval的start和end，得到他们插入之后的位置。
3. 第一个位置应该在某个Interval的左边，第二个位置则需要在某个Interval的右边，如果不是则需要调整这两个位置。
    在处理第二个位置时，对于相同元素要进一步处理：
    Input:  [[1,5]], [0,1]
    Output: [[0,1],[1,5]]
    Expected:   [[0,5]]
4. 这两个位置之间的元素都需要去掉，其它的元素保留， 所以只需将有效元素重新填到Interval数组中去即可。
*/
    // lower_bound 返回大于等于val的第一个位置
    template <typename FI, typename T>
    FI lower_bound(FI first, FI last, const T &val) {
        while(first < last) {
            FI mid = next(first, distance(first, last)/2);
            if (*mid < val) first = next(mid);
            else last = mid;
        }
        return first;
    }
    vector<Interval> insert(vector<Interval> &intervals, Interval newInterval) {
        vector<int> v2;
        for(auto i:intervals) {
            v2.push_back(i.start);
            v2.push_back(i.end);
        }
        
        auto pos1 = lower_bound(v2.begin(), v2.end(), newInterval.start); 
        pos1 = v2.insert(pos1, newInterval.start); //  插入后返回所插入元素的实际位置
        int idx1 = distance(v2.begin(), pos1); // !!! 这里必须先算出idx1,不然在下面插入newInterval.end后pos1可能会失效。
        
        // pos2肯定大于pos1,因为newInterval.end >= newInterval.start
        auto pos2 = lower_bound(v2.begin(), v2.end(), newInterval.end);
        pos2 = v2.insert(pos2, newInterval.end);
        int idx2 = distance(v2.begin(), pos2);
        
        if (idx1%2 == 1) idx1--; // 保证是一个Interval的start
        if (idx2%2 == 0) idx2++; // 保证是一个Interval的end
        
        // 进一步合并相同的元素,如[[1,5],插入[0,1]后，[0, 1][1,5] => [0,5]
        if (idx2+1 < v2.size() && v2[idx2] == v2[idx2+1]) idx2+=2;
        
        // 将有效元素填入Interval数组中。
        vector<Interval> result;
        for(int i=0; i<idx1; i+=2) result.push_back({v2[i], v2[i+1]});
        result.push_back({v2[idx1], v2[idx2]});
        for(int i=idx2+1; i<v2.size(); i+=2) result.push_back({v2[i], v2[i+1]});
        
        return result;
    }
};
