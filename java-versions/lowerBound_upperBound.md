/*
https://stackoverflow.com/questions/41958581/difference-between-upper-bound-and-lower-bound-in-stl

理解lowerBound / upperBound


value a a a b b b c c c
index 0 1 2 3 4 5 6 7 8
bound       l     u
Where l represents the lower bound of b, and u represents the upper bound of b.

So if there are range of values that are "equal" with respect to the comparison being used, 
lower_bound gives you the first of this, 
upper_bound gives you one-past-the-end of these. This is the normal pattern of STL ranges [first, last).



----------------------------------------------------
The simple answer is [ lower_bound, upper_bound )

s.lower_bound(t) will return iterator to the first element v in set such that v >= t
s.upper_bound(t) will return iterator to the first element v in set such that v > t.

When we often call xxxxx_bound for the STL set or map,
we often want to find the data in some range.

I share some usages of lower_bound & upper_bound samples here. So it could be easy for everyone to use it and remember it.

iterate all values in [A, B)
set<int> s = {0,1,2,10,11,12,15};
int A=1, B=11;
for(auto iter = s.lower_bound(A); iter != s.lower_bound(B); iter++) {
    cout<<*iter<<"\t";
}
Result

1       2       10
It show all v in set s satsify 1 < v <= 11 a.k.a all v in [1, 11)

iterate all values in [A, B]
set<int> s = {0,1,2,10,11,12,15};
int A=1, B=11;
for(auto iter = s.lower_bound(A); iter != s.upper_bound(B); iter++) {
    cout<<*iter<<"\t";
}
Result

1       2       10      11
It show all v in set s satsify 1 <= v <= 11 a.k.a all v in [1, 11]

iterate all values in (A, B)
set<int> s = {0,1,2,10,11,12,15};
int A=1, B=11;
for(auto iter = s.upper_bound(A); iter != s.lower_bound(B); iter++) {
    cout<<*iter<<"\t";
}
Result

2       10
It show all v in set s satsify 1 < v < 11 a.k.a all v in (1, 11)

Iterate all values in (A, B]
set<int> s = {0,1,2,10,11,12,15};
int A=1, B=11;
for(auto iter = s.upper_bound(A); iter != s.upper_bound(B); iter++) {
    cout<<*iter<<"\t";
}
Result

2       10      11
It show all v in set s satsify 1 < v <= 11 a.k.a all v in (1, 11]
*/






https://zhuanlan.zhihu.com/p/256416003


lower_bound 函数, 找到第一个可以插入val 的位置，并且不改变原有排序（Finds the first position in which @a val could be inserted without changing the ordering.）

upper_bound 函数, 找到最后一个可以插入val 而不改变原来有序数组的排序位置（Finds the last position in which @p __val could be inserted without changing the ordering）。
因此 upper_bound() 函数可以理解为：大于目标元素的第一个数/位置。


upper_bound() 和 lower_bound() 函数实现的都是在有序序列中查找一个可插入的位置，插入后原序列有序性不变，但是：
upper_bound() 找到的是大于目标数的位置
lower_bound() 找到的是大于等于目标数的位置


    private int lowerBound(TimeStamp target, String granularity) {
        int low = 0, high = timestampList.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            int ret = timestampList.get(mid).compareTo(target, granularity);
            if (ret < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int upperBound(TimeStamp target, String granularity) {
        int low = 0, high = timestampList.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            int ret = timestampList.get(mid).compareTo(target, granularity);
            if (ret <= 0) {  // 唯一差别在这，我们要找的大于target的位置，所以相等也要+1
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
