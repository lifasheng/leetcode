/*
very very good!
Hard

You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.
For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.


Example 1:
Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
Output: 2
Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.

Example 2:
Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
Output: -1

Constraints:
1 <= routes.length <= 500.
1 <= routes[i].length <= 105
All the values of routes[i] are unique.
sum(routes[i].length) <= 105
0 <= routes[i][j] < 106
0 <= source, target < 106
*/

class Solution {
    /*
    思路： 求最小值，考虑bfs
    建立stop到route/bus index的map，然后bfs。
    自己搞定, 赞
    */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        Map<Integer, List<Integer>> stopToRouteIndexMap = new HashMap<>();
        for (int i = 0; i < routes.length; ++i) {
            int[] route = routes[i];
            for (int stop : route) {
                stopToRouteIndexMap.putIfAbsent(stop, new ArrayList<>());
                stopToRouteIndexMap.get(stop).add(i);
            }
        }

        if (!stopToRouteIndexMap.containsKey(source) || !stopToRouteIndexMap.containsKey(target)) {
            return -1;
        }
        if (source == target) return 0;

        Set<Integer> visited = new HashSet<>(); // for visited stop
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{source, 0});
        visited.add(source);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                int[] arr = queue.poll();
                int stop = arr[0];
                int step = arr[1];
                if (stop == target) {
                    return step;
                }

                List<Integer> routeIndex = stopToRouteIndexMap.get(stop);
                for (int index : routeIndex) {
                    int[] stops = routes[index];
                    for (int s : stops) {
                        if (visited.contains(s)) continue;
                        visited.add(s);
                        queue.offer(new int[]{s, step + 1});
                    }
                }
            }
        }
        return -1;
    }
}

