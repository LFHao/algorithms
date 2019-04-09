package uber;

public class L138CopyListWithRandomPointer {
    public Node copyRandomList(Node head) {

        Node pointer = head;
        // 1st round to make copy of each node
        while (pointer != null) {
            Node copy = new Node(pointer.val, pointer.next, null);
            pointer.next = copy;
            pointer = copy.next;
        }

        // 2nd round to point to random of each node
        pointer = head;
        while (pointer != null) {
            if (pointer.random != null) {
                pointer.next.random = pointer.random.next;
            } else {
                pointer.next.random = null;
            }
            pointer = pointer.next.next;
        }

        // 3rd round to point recover original and extract copy
        pointer = head;
        Node dummy = new Node();
        Node copy = dummy, copyIter = dummy;
        Node next;
        while (pointer != null) {
            next = pointer.next.next;

            copy = pointer.next;
            copyIter.next = copy;
            copyIter = copy;

            pointer.next = next;
            pointer = pointer.next;
        }

        return dummy.next;
    }


    class Node {
        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val,Node _next,Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }
}
