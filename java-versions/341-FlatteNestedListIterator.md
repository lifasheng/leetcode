```
You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it.

Implement the NestedIterator class:

NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
int next() Returns the next integer in the nested list.
boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
Your code will be tested with the following pseudocode:

initialize iterator with nestedList
res = []
while iterator.hasNext()
    append iterator.next() to the end of res
return res
If res matches the expected flattened list, then your code will be judged as correct.

 

Example 1:

Input: nestedList = [[1,1],2,[1,1]]
Output: [1,1,2,1,1]
Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].


Example 2:

Input: nestedList = [1,[4,[6]]]
Output: [1,4,6]
Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
```


```
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
interface NestedInteger {
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}

class NestedIntegerImpl implements NestedInteger {
    private Integer number;
    private List<NestedInteger> list;
    private boolean isInteger;
    private boolean processed;

    public NestedIntegerImpl(final Integer number) {
        this.number = number;
        isInteger = true;
        processed = false;
    }

    public NestedIntegerImpl(final List<NestedInteger> list) {
        this.list = list;
        isInteger = false;
        processed = false;
    }

    public static void print(NestedInteger nestedInteger, StringBuilder stringBuilder) {
        if (nestedInteger.isInteger()) {
            stringBuilder.append(nestedInteger.getInteger());
        } else {
            List<NestedInteger> nestedIntegers = nestedInteger.getList();
            stringBuilder.append("[");
            for(NestedInteger integer : nestedIntegers) {
                print(integer, stringBuilder);
                stringBuilder.append(",");
            }
            if (!nestedIntegers.isEmpty()) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.append("]");
        }
    }

    //[[1,1],2,[1,1]]
    public static NestedInteger build(final String str) {
        Stack<NestedIntegerImpl> stack = new Stack<>();
        char[] array = str.toCharArray();
        for(int i=0; i<array.length; ++i) {
            if (array[i] == '[') {
                stack.push(new NestedIntegerImpl(new LinkedList<>()));
            } else if (array[i] == ']') {
                List<NestedInteger> list = new LinkedList<>();
                while(!stack.isEmpty()) {
                    NestedIntegerImpl nestedInteger = stack.pop();
                    if (nestedInteger.isInteger()) {
                        list.add(0, nestedInteger);
                    } else if (nestedInteger.processed) {
                        list.add(0, nestedInteger);
                    } else { // found [ placeholder
                        nestedInteger.processed = true; // mark it has been processed
                        nestedInteger.list = list;
                        stack.push(nestedInteger); // push it back to stack
                        break;
                    }
                }
            } else if (array[i] == ',' || array[i] == ' ') {
                // do nothing
            } else { // process number
                Integer integer = 0;
                while('0' <= array[i] && array[i] <= '9') {
                    integer = integer * 10 + array[i] - '0';
                    i++;
                }
                i--;
                stack.push(new NestedIntegerImpl(integer));
            }
        }
        return stack.pop();
    }

    @Override
    public boolean isInteger() {
        return isInteger;
    }

    @Override
    public Integer getInteger() {
        return isInteger ? number : null;
    }

    @Override
    public List<NestedInteger> getList() {
        return isInteger ? new ArrayList<>() : list;
    }
}


public class NestedIterator implements Iterator<Integer> {
    LinkedList<NestedInteger> nestedList;

    public NestedIterator(List<NestedInteger> nestedList) {
        this.nestedList = new LinkedList<NestedInteger>(nestedList);
    }

    @Override
    public Integer next() {
        NestedInteger ni = nestedList.removeFirst();
        return ni.getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!nestedList.isEmpty() && !nestedList.get(0).isInteger()) {
            nestedList = unflattenList(nestedList);
        }
        return !nestedList.isEmpty();
    }

    private LinkedList<NestedInteger> unflattenList(LinkedList<NestedInteger> list) {
        if (list.isEmpty() || list.get(0).isInteger()) {
            return list;
        }

        NestedInteger ni = list.removeFirst();
        LinkedList<NestedInteger> first = unflattenList(new LinkedList<>(ni.getList()));
        list.addAll(0, first);
        return list;
    }

    public static void main(String[] args) {
        String[] strings = {
//                "[[[[]]],[]]",
//                "[[1,1],2,[1,1]]",
//                "[1,[4,[6]]]",
//                "[[[1]],[2],[3,[],[[6]]]]",
                "[[[[55]]],[[31]],[99],[],75]"
        };
        for (String str : strings) {
            final NestedInteger nestedInteger = NestedIntegerImpl.build(str);
            final StringBuilder stringBuilder = new StringBuilder();
            NestedIntegerImpl.print(nestedInteger, stringBuilder);
            System.out.println(stringBuilder);
            NestedIterator i = new NestedIterator(nestedInteger.getList());
            while (i.hasNext()) {
                System.out.print(i.next() + ", ");
            }
            System.out.println();
        }
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
```