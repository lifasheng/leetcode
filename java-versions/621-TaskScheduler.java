/*
Meduim

Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle.

However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array), that is that there must be at least n units of time between any two same tasks.

Return the least number of units of times that the CPU will take to finish all the given tasks.

Example 1:
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: 
A -> B -> idle -> A -> B -> idle -> A -> B
There is at least 2 units of time between any two same tasks.

Example 2:
Input: tasks = ["A","A","A","B","B","B"], n = 0
Output: 6
Explanation: On this case any permutation of size 6 would work since n = 0.
["A","A","A","B","B","B"]
["A","B","A","B","A","B"]
["B","B","B","A","A","A"]
...
And so on.

Example 3:
Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
Output: 16
Explanation: 
One possible solution is
A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 
Constraints:
1 <= task.length <= 104
tasks[i] is upper-case English letter.
The integer n is in the range [0, 100].
*/

class Solution {
    // greedy algorithm
    /*
    思路：n越小，所需要填充的idle slot越少，比如n=0,不用填充，n=1,如果有多个字符，则交替安排就行，不用插入idle
    n越大，越有可能需要填充idle
    
    另外，不同字符越多，越不用填充idle slot，比如ABCD各有4个字符， n=3，那其实就ABCDABCDABCDABCD就足够了。
    */
    // very very good!
    public int leastInterval(char[] tasks, int n) {
        return leastInterval_greedy(tasks, n);
    }
    
    private int leastInterval_greedy(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        int[] freqs = new int[26];
        for (char c : tasks) {
            freqs[c - 'A'] ++;
        }
        
        Arrays.sort(freqs);
        
        int maxFreq = freqs[25];
        // 可能需要填充idle的数量
        int idleSlots = n * (maxFreq - 1);
        
        // 优先填充其它字符，不够了才填充idle
        // 如果maxFreq 比其他字符的freq大很多，则填充的字符可能都不够；这是maxFreq - 1 > freqs[i]的情况
        // 另外freqs[i] > maxFreq -1 的情况？, 也就是它的频率也是maxFreq, 这种情况就填充maxFreq - 1次就行，剩下的那次放在最右边就行。
        // 另外，中间填充的字符越多，就越不用填充idle，所以可以对每次都减操作。
        for (int i = 24; i >= 0 && idleSlots > 0; --i) {
            idleSlots -= Math.min(freqs[i], maxFreq - 1);
        }
        
        idleSlots = Math.max(0, idleSlots);
        
        return tasks.length + idleSlots;
    }
    
    
    // solution 2: 
    // 利用公式: https://www.cnblogs.com/grandyang/p/7098764.html
    private int leastInterval_useMath(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        int[] freqs = new int[26];
        for (char c : tasks) {
            freqs[c - 'A'] ++;
        }
        
        Arrays.sort(freqs);
        
        int maxFreq = freqs[25];
        int maxCount = 0;
        for (int i = 25; i >=0; --i) {
            if (freqs[i] == maxFreq) ++maxCount;
            else break;
        }
        
        return Math.max((maxFreq - 1) * (n + 1) + maxCount, tasks.length);
    }
}

