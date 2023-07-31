/*
Write a SQL query to get the second highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the query should return 200 as the second highest salary. If there is no second highest salary, then the query should return null.

+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+

*/


# Note that it requires reutrn null if no result, like only one row in db: [1,100], in this case, return null.

# Write your MySQL query statement below

### start ####

# normaly case
(select Salary as SecondHighestSalary
from Employee
where Salary < (select max(Salary) from Employee)
order by Salary desc
limit 1)

union

# empty result case
(
select null
)

limit 1 # make sure only one line result


### end ####
