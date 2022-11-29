/*
Medium
An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
Given an integer n, return the nth ugly number.

Example 1:
Input: n = 10
Output: 12
Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.

Example 2:
Input: n = 1
Output: 1
Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 
Constraints:
1 <= n <= 1690
*/

class Ugly_usingHeap {
    /*
    时间复杂度：O(NlogN), 弹出每个最小值时，时间复杂度都为堆的高度，因此时间复杂度为O(NlogN)
    空间复杂度：O(N), 遍历n个丑数，并将每个丑数乘以2、3和5的结果存入堆中。堆和哈希表的元素个数都不会超过n * 3。
    */
    private static final int N = 1690;
    int[] nums = new int[N];
    Ugly_usingHeap() {
        Set<Long> visited = new HashSet<>();
        PriorityQueue<Long> heap = new PriorityQueue<>();
        visited.add(1L);
        heap.add(1L);
        
        long currUgly, newUgly;
        int[] primes = new int[]{2, 3, 5};
        for (int i = 0; i < N; ++i) {
            currUgly = heap.poll();
            nums[i] = (int)currUgly;
            
            for (int j : primes) {
                newUgly = j * currUgly;
                if (!visited.contains(newUgly)) {
                    visited.add(newUgly);
                    heap.add(newUgly);
                }
            }
        }
    }
}

class Ugly_dp {
    /*    
    怎么理解呢？

    因为每个数字只能除以2、3、5，所以一种查看序的方法是将序列分成三组，如下所示：
   （1）1×2、2×2、3×2、4×2、5×2，6x2, 8x2, 9x2, 10x2, 12x2, 15x2…
   （2）1×3、2×3、3×3、4×3、5×3，6x3, 8x3, 9x3, 10x3, 12x3, 15x3…
   （3）1×5、2×5、3×5、4×5、5×5，6x5, 8x5, 9x5, 10x5, 12x5, 15x5…
    注意，这里不是1，2，3，4，5，6，7，8，9，10， 11， 这种自然数列，而是像上面用堆辅助的方法一样的思路，用当前已知的ugly序列元素分别乘以2，3，5.
    说白了，这个dp的方法就是避免使用堆，但是实现堆一样的思路。
    
    我们可以发现每个子序列都是“丑数序列本身”（这里是重点）（1、2、3、4、5，6、8、9、10、12、15、16、…）乘以2、3、5。
    然后我们使用类似的归并方法作为归并排序，从三个序列中获取每个丑数子序列。 每一步我们都选择最小的一步，然后再向前迈一步。
    */
    
    private static final int N = 1690;
    int[] nums = new int[N];
    
    Ugly_dp() {
        nums[0] = 1;
        // p2,p3,p5 分别表示三个子序列的下标，而巧妙的是，这三个子序列其实共用了最终的那个ugly 序列（即nums）
        // 因为每个子序列都是“丑数序列本身”分别乘以2、3、5
        int p2 = 0, p3 = 0, p5 = 0;
        int uglyNum;
        for (int i = 1; i < N; ++i) {
            int ugly2 = nums[p2] * 2;
            int ugly3 = nums[p3] * 3;
            int ugly5 = nums[p5] * 5;
            uglyNum = Math.min(ugly2, Math.min(ugly3, ugly5));
            nums[i] = uglyNum;
            
            // 注意这里不能用else，因为uglyNum可能同时等于ugly2，ugly3等，此时相当于两个子序列的头部元素相等，比如2x3==3x2
            // 这种情况向，我们为了去重，两个都要向后移动。
            if (uglyNum == ugly2) {
                ++ p2;
            }
            if (uglyNum == ugly3) {
                ++ p3;
            }
            if (uglyNum == ugly5) {
                ++ p5;
            }
        }
    }
}

class Solution {
    // very good!
    public int nthUglyNumber(int n) {
        Ugly_dp ugly = new Ugly_dp();
        return ugly.nums[n - 1];
    }
}

