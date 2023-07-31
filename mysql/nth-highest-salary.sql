/*
Write a SQL query to get the nth highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the nth highest salary where n = 2 is 200. If there is no nth highest salary, then the query should return null.

+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+
*/

CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  RETURN (
      # Write your MySQL query statement below.
      
      # 1. if there is less then N distinct salary, we need to return null
      # 2. note that distinct here is important, [100, 100, 100], N = 2, it should return null.
      select null
      from Employee 
      where N > (select count(distinct Salary) from Employee)
      
      
      union
      
      # 1. normal case, we first get the top distinct N salary, then reverse it and get the Nth one.
      # 2. also, distinct here is important, [100, 200, 300, 300, 400], N = 3 , it should return 200.
      (
      select Salary
      from (select  distinct Salary from Employee order by Salary desc limit N) tmp
      order by tmp.Salary asc
      limit 1
      )
      
      limit 1
      
  );
END

