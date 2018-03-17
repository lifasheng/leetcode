Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, DepartmentId int)
Truncate table Employee
insert into Employee (Id, Name, Salary, DepartmentId) values ('1', 'Joe', '70000', '1')
insert into Employee (Id, Name, Salary, DepartmentId) values ('2', 'Henry', '80000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('3', 'Sam', '60000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('4', 'Max', '90000', '1')


get the 3rd max salary:

1. using limit offset:
select salary from employee order by salary desc limit 2,1;

2. do not use limit:
select salary from employee e1 where 3 = (select count(*) from employee e2 where e2.salary >= e1.salary);

3. use auto-increment temporary table:
SELECT salary FROM (SELECT @rn := @rn + 1 rn, a.salary from employee a, (SELECT @rn := 0) b   ORDER BY salary DESC ) sub WHERE sub.rn = 3;


mysql> SELECT @rn := @rn + 1 rn, a.salary from employee a, (SELECT @rn := 0) b   ORDER BY salary DESC;
+------+--------+
| rn   | salary |
+------+--------+
|    1 |  90000 |
|    2 |  85000 |
|    3 |  80000 |
|    4 |  70000 |
|    5 |  69000 |
|    6 |  60000 |
+------+--------+
6 rows in set (0.00 sec)


