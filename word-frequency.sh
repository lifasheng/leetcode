/*
Write a bash script to calculate the frequency of each word in a text file words.txt.

For simplicity sake, you may assume:

words.txt contains only lowercase characters and space ' ' characters.
Each word must consist of lowercase characters only.
Words are separated by one or more whitespace characters.
For example, assume that words.txt has the following content:

the day is sunny the the
the sunny is is
Your script should output the following, sorted by descending frequency:
the 4
is 3
sunny 2
day 1
Note:
Don't worry about handling ties, it is guaranteed that each word's frequency count is unique.
*/


# Read from the file words.txt and output the word frequency list to stdout.
cat words.txt | awk '{ for(i=1; i<=NF; i++) word[$i]++; color[$i]++; } END { for(j in word) printf("%s %d\n", j, word[j]) }' | sort -k 2 -n -r

# note that awk supports Associative Arrays, like map: http://wanggen.myweb.hinet.net/ach3/ach3.html
# NF(Number of Fields)為儲存每一行的欄位數,而 NR(Number of Records)為檔案的行數(awk 所讀入的行其術語叫〝record〞)。

# sort by second column: -k 2, -n menas numeric, -r means reverse

