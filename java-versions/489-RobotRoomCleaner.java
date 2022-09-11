/*
Hard

You are controlling a robot that is located somewhere in a room. The room is modeled as an m x n binary grid where 0 represents a wall and 1 represents an empty slot.

The robot starts at an unknown location in the room that is guaranteed to be empty, and you do not have access to the grid, but you can move the robot using the given API Robot.

You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room). The robot with the four given APIs can move forward, turn left, or turn right. Each turn is 90 degrees.

When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, and it stays on the current cell.

Design an algorithm to clean the entire room using the following APIs:

interface Robot {
  // returns true if next cell is open and robot moves into the cell.
  // returns false if next cell is obstacle and robot stays on the current cell.
  boolean move();

  // Robot will stay on the same cell after calling turnLeft/turnRight.
  // Each turn will be 90 degrees.
  void turnLeft();
  void turnRight();

  // Clean the current cell.
  void clean();
}
Note that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all surrounded by a wall.

Custom testing:

The input is only given to initialize the room and the robot's position internally. You must solve this problem "blindfolded". In other words, you must control the robot using only the four mentioned APIs without knowing the room layout and the initial robot's position.

Example 1:
Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]], row = 1, col = 3
Output: Robot cleaned all rooms.
Explanation: All grids in the room are marked by either 0 or 1.
0 means the cell is blocked, while 1 means the cell is accessible.
The robot initially starts at the position of row=1, col=3.
From the top left corner, its position is one row below and three columns right.

Example 2:
Input: room = [[1]], row = 0, col = 0
Output: Robot cleaned all rooms.

Constraints:
m == room.length
n == room[i].length
1 <= m <= 100
1 <= n <= 200
room[i][j] is either 0 or 1.
0 <= row < m
0 <= col < n
room[row][col] == 1
All the empty cells can be visited from the starting position.
*/

/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

/*
https://www.cnblogs.com/grandyang/p/9988250.html
这道题就是经典的扫地机器人的题目了，之前经常在地里看到这道题，终于被 LeetCode 收录了进来了，也总算是找到了一个好的归宿了。回归题目，给了我们一个扫地机器人，给了4个 API 函数可供调用，具体实现不用操心，让我们实现打扫房间 cleanRoom 函数。给的例子中有房间和起始位置的信息，但是代码中却没有，摆明是不想让我们分心。想想也是，难道在给扫地机器人编程时，还必须要知道用户的房间信息么？当然不能够啦，题目中也说了让盲目 Blindfolded 一些，所以就盲目的写吧。既然是扫地，那么肯定要记录哪些位置已经扫过了，所以肯定要记录位置信息，由于不知道全局位置，那么只能用相对位置信息了。初始时就是 (0, 0)，然后上下左右加1减1即可。位置信息就放在一个 HashSet 中就可以了，同时为了方便，还可以将二维坐标编码成一个字符串。这里采用递归 DFS 来做，初始化位置为 (0, 0)，然后建一个上下左右的方向数组，使用一个变量 dir 来从中取数。在递归函数中，首先对起始位置调用 clean 函数，因为题目中说了起始位置是能到达的，即是为1的地方。然后就要把起始位置加入 visited。然后循环四次，因为有四个方向，由于递归函数传进来的 dir 是上一次转到的方向，那么此时 dir 加上i，为了防止越界，对4取余，就是新的方向了，然后算出新的位置坐标 newX 和 newY。此时先要判断 visited 不含有这个新位置，即新位置没有访问过，还要调用 move 函数来确定新位置是否可以到达，若这两个条件都满足的话，就对新位置调用递归函数。注意递归函数调用完成后，要回到调用之前的状态，因为这里的 robot 是带了引用号的，是全局通用的，所以要回到之前的状态。回到之前的状态很简单，因为这里的机器人的运作方式是先转到要前进的方向，才能前进。那么后退的方法就是，旋转 180 度，前进一步，再转回到原来的方向。同理，在按顺序试上->右->下->左的时候，每次机器人要向右转一下，因为 move 函数只能探测前方是否能到达，所以必须让机器人转到正确的方向，才能正确的调用 move 函数。如果用过扫地机器人的童鞋应该会有影响，当前方有障碍物的时候，机器人圆盘会先转个方向，然后再继续前进，这里要实现的机制也是类似的.
*/
class Solution {
    // very very good!
    // going clockwise : 0: 'up', 1: 'right', 2: 'down', 3: 'left'
    private static final int[][] DIRECTIONS = {
        {-1, 0}, //up
        {0, 1}, // right
        {1, 0}, // down
        {0, -1} // left
    };
    public void cleanRoom(Robot robot) {
        // 这里的坐标是相对坐标，以起始点为(0,0)来建立的。
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        dfs(robot, 0, 0, 0, visited);
    }
    
    private void dfs(Robot robot, int r, int c, int dir, Set<Pair<Integer, Integer>> visited) {
        visited.add(new Pair(r, c));
        robot.clean();
        
        // going clockwise : 0: 'up', 1: 'right', 2: 'down', 3: 'left'
        for (int i = 0; i < 4; ++i) {
            int newDir = (dir + i) % 4;
            int newR = r + DIRECTIONS[newDir][0];
            int newC = c + DIRECTIONS[newDir][1];
            
            // 如果新的位置没有被访问过，并且可以move到达，则递归处理，递归后要回到原来的位置和方向。
            // 可以用四个cell来模拟一遍，感受一下
            if (!visited.contains(new Pair(newR, newC)) && robot.move()) {
                dfs(robot, newR, newC, newDir, visited);
                goBack(robot);
            }
            
            // 处理完每个方向之后都右转进入下一个方向，只使用右转
            robot.turnRight();
        }
    }
    
    private void goBack(Robot robot) {
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }
}


