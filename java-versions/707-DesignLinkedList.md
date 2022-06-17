```
Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node.
If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement the MyLinkedList class:

MyLinkedList() Initializes the MyLinkedList object.
int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.
void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
void addAtTail(int val) Append a node of value val as the last element of the linked list.
void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. If index equals the length of the linked list, the node will be appended to the end of the linked list. If index is greater than the length, the node will not be inserted.
void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.
 

Example 1:

Input
["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
[[], [1], [3], [1, 2], [1], [1], [1]]
Output
[null, null, null, null, 2, null, 3]

Explanation
MyLinkedList myLinkedList = new MyLinkedList();
myLinkedList.addAtHead(1);
myLinkedList.addAtTail(3);
myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
myLinkedList.get(1);              // return 2
myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
myLinkedList.get(1);              // return 3
```


```
class MyLinkedList {
    class ListNode {
        private int val;
        private ListNode next;
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    private ListNode head;
    private ListNode tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }

        if (index == 0) {
            return head.val;
        }
        if (index == size-1) {
            return tail.val;
        }
        // 1->2>->3->4
        ListNode p = head;
        while(index > 0) {
            p = p.next;
            index --;
        }
        return p.val;
    }

    public void addAtHead(int val) {
        ListNode node = new ListNode(val, head);
        head = node;
        if (tail == null) {
            tail = node;
        }
        ++ size;
    }

    public void addAtTail(int val) {
        ListNode node = new ListNode(val);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;

        ++size;
    }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) {
            return;
        }

        if (index == 0) {
            addAtHead(val);
            return;
        }
        if (index == size) {
            addAtTail(val);
            return;
        }

        // 1->2->3->4
        ListNode dummy = new ListNode(-1, head);
        ListNode p = dummy;
        while (index > 0) {
            p = p.next;
            --index;
        }

        ListNode node = new ListNode(val, p.next);
        p.next = node;
        ++size;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        if (size == 1) {
            size = 0;
            head = null;
            tail = null;
            return;
        }

        --size;
        if (index == 0) {
            head = head.next;
            return;
        }

        // 1->2->3->4
        ListNode dummy = new ListNode(-1, head);
        ListNode p = dummy;
        while (index > 0) {
            p = p.next;
            --index;
        }
        if (p.next == tail) {
            tail = p;
        }
        p.next = p.next.next;

    }

    public void print() {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + ", ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] methods = {"addAtHead","get","addAtHead","addAtHead","deleteAtIndex","addAtHead","get","get","get","addAtHead","deleteAtIndex"};
        int[][] params = {{4},{1},{1},{5},{3},{7},{3},{3},{3},{1},{4}};
        MyLinkedList list = new MyLinkedList();
        for (int i=0; i<methods.length; ++i) {
            switch (methods[i]) {
                case "addAtHead":
                    list.addAtHead(params[i][0]);
                    break;
                case "get":
                    int v = list.get(params[i][0]);
                    System.out.println(v);
                    break;
                case "addAtTail":
                    list.addAtTail(params[i][0]);
                    break;
                case "addAtIndex":
                    list.addAtIndex(params[i][0], params[i][1]);
                    break;
                case "deleteAtIndex":
                    list.deleteAtIndex(params[i][0]);
                    break;
            }
            list.print();
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
```
