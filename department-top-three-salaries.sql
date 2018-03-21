/*
The Employee table holds all employees. Every employee has an Id, and there is also a column for the department Id.

Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, DepartmentId int)
Create table If Not Exists Department (Id int, Name varchar(255))
Truncate table Employee
insert into Employee (Id, Name, Salary, DepartmentId) values ('1', 'Joe', '70000', '1')
insert into Employee (Id, Name, Salary, DepartmentId) values ('2', 'Henry', '80000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('3', 'Sam', '60000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('4', 'Max', '90000', '1')
Truncate table Department
insert into Department (Id, Name) values ('1', 'IT')
insert into Department (Id, Name) values ('2', 'Sales')

+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 70000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
+----+-------+--------+--------------+
The Department table holds all departments of the company.

+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
Write a SQL query to find employees who earn the top three salaries in each of the department. For the above tables, your SQL query should return the following rows.

+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Randy    | 85000  |
| IT         | Joe      | 70000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |
+------------+----------+--------+

Note that if two employees have same salary, then they are considered as one.
------------------
For input: {"headers": {"Employee": ["Id", "Name", "Salary", "DepartmentId"], "Department": ["Id", "Name"]}, "rows": {"Employee": [[1, "Joe", 60000, 1], [2, "Ralph", 50000, 1], [3, "Joel", 60000, 1], [4, "Tracy", 75000, 1]], "Department": [[1, "IT"]]}}
Expected:  {"headers":["Department","Employee","Salary"],"values":[["IT","Tracy",75000],["IT","Joe",60000],["IT","Joel",60000],["IT","Ralph",50000]]}
my original output: {"headers":["Department","Employee","Salary"],"values":[["IT","Tracy",75000],["IT","Joe",60000],["IT","Joel",60000]]}
------------------
*/



# Write your MySQL query statement below
/*
# How did we think about the idea?
# 1. we can get the Nth record for all table, we can use count(*) or limit offset. But it is not enough, we need to get it by departmentId using group by.
# 2. I search how to get Nth record for each group.
mysql> select departmentId, (select salary from employee e1 where e1.departmentId = e2.departmentId limit 2,1) as salary  from employee e2 group by departmentId;
+--------------+--------+
| departmentId | salary |
+--------------+--------+
|            1 |  69000 |
|            2 |   NULL |
+--------------+--------+
2 rows in set (0.00 sec)

# This is a big step already, right?

but wait, we need to get the minimum one if there are less than 3 rows for departmentId = 2.

I try to use union:

mysql> select departmentId, ( (select salary from employee e1 where e1.departmentId = e2.departmentId order by salary desc limit 2,1 ) union (select min(salary) from employee) limit 1) as salary  from employee e2 group by departmentId;
but it has grammer error.


So I try to join with another drived table as below:

mysql> select t1.departmentId, case when t1.salary is null then t2.salary else t1.salary end as salary 
    from (
        select departmentId, 
        (select distinct salary from employee e1 where e1.departmentId = e2.departmentId order by salary desc limit 2,1 ) as salary  
        from employee e2 group by departmentId
    ) t1, 
    (select departmentId, min(salary) as salary from employee e4 group by departmentId) t2 
    where t1.departmentId = t2.departmentId 
    
It works:
+--------------+--------+
| departmentId | salary |
+--------------+--------+
|            1 |  70000 |
|            2 |  60000 |
+--------------+--------+
2 rows in set (0.00 sec)

# 3. Ok, so we have get the Nth row for each group, then we can join with the original table using departmentId to get all rows whose salary is greater than it.

mysql> select * from employee e3, ( select t1.departmentId, case when t1.salary is null then t2.salary else t1.salary end as salary from (select departmentId, (select salary from employee e1 where e1.departmentId = e2.departmentId order by salary desc limit 2,1 ) as salary  from employee e2 group by departmentId) t1, (select departmentId, min(salary) as salary from employee e4 group by departmentId) t2 where t1.departmentId = t2.departmentId ) tmp where e3.departmentId = tmp.departmentId  and e3.salary >= tmp.salary;
+------+-------+--------+--------------+--------------+--------+
| Id   | Name  | Salary | DepartmentId | departmentId | salary |
+------+-------+--------+--------------+--------------+--------+
|    4 | Max   |  90000 |            1 |            1 |  70000 |
|    1 | Joe   |  70000 |            1 |            1 |  70000 |
|    6 | Randy |  85000 |            1 |            1 |  70000 |
|    3 | Sam   |  60000 |            2 |            2 |  60000 |
|    2 | Henry |  80000 |            2 |            2 |  60000 |
+------+-------+--------+--------------+--------------+--------+
5 rows in set (0.02 sec)

Ok, we just need to get the expected columns.

# 4. Note that distinct here is important to solve the above test case: two employees have same salary.
*/



select d.name as Department, e3.name as Employee,  e3.salary as Salary 
from employee e3, 
( 
    select t1.departmentId, case when t1.salary is null then t2.salary else t1.salary end as salary 
    from (
        select departmentId, 
        (select distinct salary from employee e1 where e1.departmentId = e2.departmentId order by salary desc limit 2,1 ) as salary  
        from employee e2 group by departmentId
    ) t1, 
    (select departmentId, min(salary) as salary from employee e4 group by departmentId) t2 
    where t1.departmentId = t2.departmentId 
) tmp, 
department d 
where e3.departmentId = tmp.departmentId  
and e3.salary >= tmp.salary 
and e3.departmentId = d.Id 
order by d.name, e3.salary desc;

