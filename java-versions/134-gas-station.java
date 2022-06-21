/*
134. Gas Station
Medium
There are N gas stations along a circular route, where the amount of gas at station i is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

Note:

If there exists a solution, it is guaranteed to be unique.
Both input arrays are non-empty and have the same length.
Each element in the input arrays is a non-negative integer.
Example 1:

Input: 
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]

Output: 3

Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
Example 2:

Input: 
gas  = [2,3,4]
cost = [3,4,3]

Output: -1

Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.

*/

class Solution {
    // Time: O(N^2), 将每个位置都当做起始点进行尝试。
    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int n = gas.length;
        for (int i=0; i<n; ++i) {
            int j = i;
            int leftGas=0;
            while(true) {
                leftGas += (gas[j] - cost[j]);
                if (leftGas < 0) {
                    break;
                }
                j = (j+1)%n;
                if (j == i) { // 回到了起始点
                    break;
                }
            }
            if (leftGas >= 0) {
                return i;
            }
        }
        return -1;
    }
    
    /*
    Let's introduce curr_tank variable to track the current amount of gas in the tank. 
    If at station i, curr_tank is less than 0, that means that one couldn't reach station i+1.

    Next step is to mark station i+1 as a new starting point, 
    and reset curr_tank to zero since one starts with no gas in the tank.
    
    If total_tank >= 0, and we can reach station n-1 by starting from station X, then we can definitely loop back to reach station X by starting from n-1. This can be proofed using contradiction. (反证法). 
    So we don't need to worry about if it can loop back when we can reach the n-1 station in one pass.
    */
    // Time: O(N), Space: O(1)
    // https://www.youtube.com/watch?v=rf66wlb9aNQ&feature=youtu.be
    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int n = gas.length;
        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for(int i=0; i<n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i+1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return (total_tank >= 0) ? starting_station : -1;
    }
    
    public int canCompleteCircuit(int[] gas, int[] cost) {
        return canCompleteCircuit2(gas, cost);
    }
}


