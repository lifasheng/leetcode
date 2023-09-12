/*
very good!

The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 

Example 1:

Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
 

Constraints:

-105 <= num <= 105
There will be at least one element in the data structure before calling findMedian.
At most 5 * 104 calls will be made to addNum and findMedian.
 

Follow up:
If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
*/


class MedianFinder {

    private PriorityQueue<Integer> maxPQ;
    private PriorityQueue<Integer> minPQ;

    public MedianFinder() {
        maxPQ = new PriorityQueue<>((a, b) -> b - a); // new PriorityQueue(Collections.reverseOrder());
        minPQ = new PriorityQueue<>((a, b) -> a - b); // new PriorityQueue<>();
    }
    
    /**
     左边的最大堆比右边的最小堆个数相等或大一
     */
    public void addNum(int num) {
        if (maxPQ.size() == minPQ.size()) {
            maxPQ.offer(num);

            if (minPQ.isEmpty()) return;
        } else {
            minPQ.offer(num);
        }

        if (maxPQ.peek() > minPQ.peek()) {
            int min = minPQ.poll();
            int max = maxPQ.poll();
            maxPQ.offer(min);
            minPQ.offer(max);
        }
    }
    
    public double findMedian() {
        if (maxPQ.size() > minPQ.size()) {
            return maxPQ.peek();
        } else {
            return (maxPQ.peek() + minPQ.peek()) / 2.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */



// https://github.com/RodneyShag/LeetCode_solutions/blob/master/Solutions/Find%20Median%20from%20Data%20Stream.md

Followup #1 - If all integer numbers from the stream are between 0 and 100, how would you optimize it
Create 101 buckets using an array of size 101.
Store the numbers into these buckets.
Find median by looping through this array.
Time Complexity
addNum() is O(1)
findMedian() is O(1) since array has fixed size.
Space Complexity: O(1) since array has fixed size.
Sample problem: https://leetcode.com/problems/statistics-from-a-large-sample/description/

