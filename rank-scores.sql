/*

Write a SQL query to rank scores. If there is a tie between two scores, both should have the same ranking. Note that after a tie, the next ranking number should be the next consecutive integer value. In other words, there should be no "holes" between ranks.

+----+-------+
| Id | Score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+
For example, given the above Scores table, your query should generate the following report (order by highest score):

+-------+------+
| Score | Rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+

*/

# Write your MySQL query statement below

# below method use a temporary table to generate auto-increment id, it works in mysql console, 
# but not in leetcode test env because of permission issue I guess.
# Create TEMPORARY table Ranks (Rank int not null primary key auto_increment, Score DECIMAL(3,2) )
# Truncate table Ranks
# INSERT INTO Ranks(Score) SELECT distinct Score FROM Scores order by Score desc
# Select r.Score, r.Rank from Scores s, Ranks r where s.Score = r.Score order by s.Score desc

# This way of generating auto-increment id column is so cool.

# 1. cross join: https://www.w3resource.com/mysql/advance-query-in-mysql/mysql-cross-join.php
/*
mysql> select *  from (select distinct Score from Scores order by Score desc) t cross join (select @cnt := 0) as dummy;
+-------+-----------+
| Score | @cnt := 0 |
+-------+-----------+
|  4.00 |         0 |
|  3.85 |         0 |
|  3.65 |         0 |
|  3.50 |         0 |
+-------+-----------+
4 rows in set (0.01 sec)

*/
# 2. distinct is important here when generate the intermediate table r.

Select r.Score, r.Rank 
from Scores s, 
( SELECT (@cnt := @cnt + 1) AS  Rank, t.* from (select distinct Score from Scores order by Score desc) t cross join (select @cnt := 0) as dummy ) as r 
where s.Score = r.Score order by s.Score desc


