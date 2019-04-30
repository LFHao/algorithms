package typical.OODesign;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    int capacity;
    LRUNode head;
    LRUNode tail;
    Map<Integer, LRUNode> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new LRUNode();
        tail = new LRUNode();
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        LRUNode node = map.get(key);

        // cut the node
        // Time Complexity of cutting the node is O(1)
        node.next.prev = node.prev;
        node.prev.next = node.next;

        // move the node to tail
        moveToTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        // update the value and move to tail
        if (map.containsKey(key)) {
            map.get(key).value = value;
            get(key);
            return;
        }

        // if capacity is full, remove a node from the head
        if (map.size() == capacity) {
            // you also need to remove the key from map
            map.remove(key);
            head.next = head.next.next;
            head.next.prev = head;
        }

        // new node
        LRUNode node = new LRUNode(key, value);
        moveToTail(node);
    }

    // connect the node before tail is O(1)
    private void moveToTail(LRUNode node) {
        node.prev = tail.prev;
        tail.prev = node;
        node.prev.next = node;
        node.next = tail;
    }

    class LRUNode {
        int key;
        int value;
        LRUNode prev;
        LRUNode next;

        LRUNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        LRUNode() {}
    }
}
