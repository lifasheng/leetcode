Given employee table, find the Nth max salary of each department.

Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, DepartmentId int)
insert into Employee (Id, Name, Salary, DepartmentId) values ('1', 'Joe', '70000', '1')
insert into Employee (Id, Name, Salary, DepartmentId) values ('2', 'Henry', '80000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('3', 'Sam', '60000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('4', 'Max', '90000', '1')

mysql> select * from employee;
+------+-------+--------+--------------+
| Id   | Name  | Salary | DepartmentId |
+------+-------+--------+--------------+
|    4 | Max   |  90000 |            1 |
|    3 | Sam   |  60000 |            2 |
|    2 | Henry |  80000 |            2 |
|    1 | Joe   |  70000 |            1 |
|    5 | Janet |  69000 |            1 |
|    6 | Randy |  85000 |            1 |
+------+-------+--------+--------------+
6 rows in set (0.00 sec)

mysql> SELECT   departmentId, (   SELECT   salary   FROM     employee t2   WHERE    t2.departmentId = t1.departmentId   ORDER BY salary DESC   LIMIT    1, 1 ) as secondMax FROM     employee t1 GROUP BY departmentId;
+--------------+-----------+
| departmentId | secondMax |
+--------------+-----------+
|            1 |     85000 |
|            2 |     60000 |
+--------------+-----------+
2 rows in set (0.00 sec)

------------------------------------------------------------------------------
Note that the subquery can only return 1 row, otherwise, it will report error.

mysql> SELECT   departmentId, (   SELECT   salary   FROM     employee t2   WHERE    t2.departmentId = t1.departmentId   ORDER BY salary DESC   LIMIT    1, 2 ) FROM     employee t1 GROUP BY departmentId;
ERROR 1242 (21000): Subquery returns more than 1 row

