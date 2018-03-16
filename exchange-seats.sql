/*
Mary is a teacher in a middle school and she has a table seat storing students' names and their corresponding seat ids.

The column id is continuous increment.
Mary wants to change seats for the adjacent students.
Can you write a SQL query to output the result for Mary?
+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Abbot   |
|    2    | Doris   |
|    3    | Emerson |
|    4    | Green   |
|    5    | Jeames  |
+---------+---------+
For the sample input, the output is:
+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Doris   |
|    2    | Abbot   |
|    3    | Green   |
|    4    | Emerson |
|    5    | Jeames  |
+---------+---------+

*/

# Write your MySQL query statement below

# step 1. use seat left join seat to get odd rows. Here we need left join because the number of table may be odd.
/*
+------+---------+
| id   | student |
+------+---------+
|    1 | Doris   |
|    3 | Green   |
|    5 | Jeames  |
+------+---------+
*/
# step 2. use seat inner join seat to get even rows. Here we use inner join for even.
/*
+------+---------+
| id   | student |
+------+---------+
|    2 | Abbot   |
|    4 | Emerson |
+------+---------+
*/
# step 3. union result of step 1 and 2, order by id to get final result.

select s1.id, (case when s2.student is null then s1.student else s2.student end) as student from seat s1 left join  seat s2 on  s1.id+1 = s2.id where s1.id % 2 = 1
union
select s1.id, s2.student from seat s1 inner join seat s2 on s1.id = s2.id+1 where s1.id%2 = 0
order by id
