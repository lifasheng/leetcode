/*
146. LRU Cache
Medium
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists, otherwise return -1.
void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
Follow up:
Could you do get and put in O(1) time complexity?

 

Example 1:

Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
 

Constraints:

1 <= capacity <= 3000
0 <= key <= 3000
0 <= value <= 104
At most 3 * 104 calls will be made to get and put.
*/

/*
Hashmap + DoubleLinkedList
双向链表插入和删除效率高，且不用遍历链表。
这里使用两个哨兵node，head和tail，方便处理很多edge case。

注意：Java里的LinkedList也是双向链表，但是它没有expose node的reference，
所以没法把指针存到map中，只能自己实现一个双向链表。
*/
class LRUCache {
    class DDLNode {
        int key;
        int value;
        DDLNode prev;
        DDLNode next;
        DDLNode(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }
    
    class DoublyLinkedList {
        DDLNode head;
        DDLNode tail;
        DoublyLinkedList() {
            this.head = new DDLNode(-1, -1);
            this.tail = new DDLNode(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        
        void addNode(DDLNode node) {
            node.prev = head;
            node.next = head.next;
            
            head.next.prev = node;
            head.next = node;
        }
        
        void removeNode(DDLNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        
        void moveToHead(DDLNode node) {
            removeNode(node);
            addNode(node);
        }
        
        DDLNode popTail() {
            DDLNode node = tail.prev;
            removeNode(node);
            return node;
        }   
    }
    
    int capacity;
    Map<Integer, DDLNode> map = new HashMap<>();
    DoublyLinkedList list = new DoublyLinkedList();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        DDLNode node = map.get(key);
        if (node == null) return -1;
        list.moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) { // 这个情况一定要记得处理，并且把node移动到list前面。
            DDLNode node = map.get(key);
            node.value = value;
            list.moveToHead(node);
            return;
        }
        
        if (map.size() >= capacity) {
            DDLNode node = list.popTail();
            map.remove(node.key);
        }
        DDLNode node = new DDLNode(key, value);
        list.addNode(node);
        map.put(key, node);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */




class LRUCache {
    
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    
    class DoublyLinkedList {
        Node head;
        Node tail;
        
        void addTail(Node node) {
            if (tail == null) {
                head = node;
                tail = node;
            } else {
                node.prev = tail;
                tail.next = node;
                tail = node;
            }
        }
        
        void removeTail() {
            if (tail == null) return;
            if (tail.prev == null) {
                head = null;
                tail = null;
                return;
            }
            
            Node prev = tail.prev;
            prev.next = null;
            tail = prev;
        }
        
        Node removeHead() {
            Node node = head;
            if (head.next == null) {
                head = null;
                tail = null;
            } else {
                Node next = head.next;
                next.prev = null;
                head = next;
            }
            return node;
        }
        
        void removeNode(Node node) {
            if (node == head) {
                removeHead();
                return;
            }
            
            if (node == tail) {
                removeTail();
                return;
            }
            
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            
            node.prev = null;
            node.next = null;
        }
    }

    Map<Integer, Node> map;
    DoublyLinkedList list;
    int cap;
    public LRUCache(int capacity) {
        map = new HashMap<>();
        list = new DoublyLinkedList();
        cap = capacity;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        
        Node node = map.get(key);
        list.removeNode(node);
        list.addTail(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            list.removeNode(node);
            list.addTail(node);
        } else {
            if (map.size() >= cap) {
                Node node = list.removeHead();
                map.remove(node.key);
            }
            
            Node node = new Node(key, value);
            list.addTail(node);
            map.put(key, node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

