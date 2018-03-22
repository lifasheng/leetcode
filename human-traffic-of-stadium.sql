/*
Create table If Not Exists stadium (id int, date DATE NULL, people int)
Truncate table stadium
insert into stadium (id, date, people) values ('1', '2017-01-01', '10')
insert into stadium (id, date, people) values ('2', '2017-01-02', '109')
insert into stadium (id, date, people) values ('3', '2017-01-03', '150')
insert into stadium (id, date, people) values ('4', '2017-01-04', '99')
insert into stadium (id, date, people) values ('5', '2017-01-05', '145')
insert into stadium (id, date, people) values ('6', '2017-01-06', '1455')
insert into stadium (id, date, people) values ('7', '2017-01-07', '199')
insert into stadium (id, date, people) values ('8', '2017-01-08', '188')

X city built a new stadium, each day many people visit it and the stats are saved as these columns: id, date, people

Please write a query to display the records which have 3 or more consecutive rows and the amount of people more than 100(inclusive).

For example, the table stadium:
+------+------------+-----------+
| id   | date       | people    |
+------+------------+-----------+
| 1    | 2017-01-01 | 10        |
| 2    | 2017-01-02 | 109       |
| 3    | 2017-01-03 | 150       |
| 4    | 2017-01-04 | 99        |
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-08 | 188       |
+------+------------+-----------+
For the sample data above, the output is:

+------+------------+-----------+
| id   | date       | people    |
+------+------------+-----------+
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-08 | 188       |
+------+------------+-----------+
Note:
Each day only have one row record, and the dates are increasing with id increasing.

*/

# Write your MySQL query statement below

/*
select distinct *
from
(
select distinct s1.id, s1.date, s1.people
from stadium s1, stadium s2, stadium s3
where 
s1.id + 1 = s2.id 
and s2.id +1 = s3.id
and s1.people >= 100 and s2.people >= 100 and s3.people >= 100

union

select distinct s2.id, s2.date, s2.people
from stadium s1, stadium s2, stadium s3
where 
s1.id + 1 = s2.id 
and s2.id +1 = s3.id
and s1.people >= 100 and s2.people >= 100 and s3.people >= 100

union

select distinct s3.id, s3.date, s3.people
from stadium s1, stadium s2, stadium s3
where 
s1.id + 1 = s2.id 
and s2.id +1 = s3.id
and s1.people >= 100 and s2.people >= 100 and s3.people >= 100
) tmp
order by id

*/


SELECT distinct s1.* FROM stadium AS s1, stadium AS s2, stadium as s3
    WHERE 
    ((s1.id + 1 = s2.id
    AND s1.id + 2 = s3.id)
    OR 
    (s1.id - 1 = s2.id
    AND s1.id + 1 = s3.id)
    OR
    (s1.id - 2 = s2.id
    AND s1.id - 1 = s3.id)
    )
    AND s1.people>=100 
    AND s2.people>=100
    AND s3.people>=100

    order BY s1.id

