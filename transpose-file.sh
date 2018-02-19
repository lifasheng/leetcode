/*
Given a text file file.txt, transpose its content.

You may assume that each row has the same number of columns and each field is separated by the ' ' character.

For example, if file.txt has the following content:

name age
alice 21
ryan 30
Output the following:

name alice ryan
age 21 30
*/

# Read from the file file.txt and print its transposed content to stdout.
# NF: number of filed in each row
# NR: number of record, which line
awk '            
{            
    for (i=1; i<=NF; i++)  {
        a[NR,i] = $i;
    }; col = NF; 
}
END {
    for(j=1; j<=col; j++) {
        str=a[1,j]
        for(i=2; i<=NR; i++){
            str=str" "a[i,j];
        }
        print str
    }
}' file.txt
