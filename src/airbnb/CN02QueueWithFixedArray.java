package airbnb;

import java.util.ArrayList;
import java.util.List;

// Use the last element of arraylist to store the next arraylist
public class CN02QueueWithFixedArray {
    int count;
    int head;
    int tail;
    List<Object> headList;
    List<Object> tailList;
    int fixedNum;

    CN02QueueWithFixedArray(int fixedNum) {
        count = 0;
        head = 0;
        tail = 0;
        this.fixedNum = fixedNum;
        headList = new ArrayList<>(fixedNum);
        tailList = headList;
    }

    public void offer(Object object) {
        if (tail == fixedNum - 1) {
            List<Object> newList = new ArrayList<>();
            newList.add(object);
            tailList.add(newList); // operate the queue arraylist
            tailList = (List<Object>) tailList.get(tail); // tail list always point to the last arraylist
            tail = 0;
        } else {
            tailList.add(object);
        }

        tail++;
        count++;
    }

    public Object poll() {

        Object object = headList.get(head);
        head++;
        count--;

        // head could always be smaller than fixedNum - 1
        if (head == fixedNum - 1) {
            List<Object> nextList = (List<Object>) headList.get(head);
            headList.clear();
            headList = nextList;
            head = 0;
        }


        return object;
    }

    public int size() {
        return count;
    }

    public static void main(String[] args) {
        CN02QueueWithFixedArray queue = new CN02QueueWithFixedArray(5);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        System.out.println((int)queue.poll());
        System.out.println((int)queue.poll());
        System.out.println((int)queue.poll());
        System.out.println((int)queue.poll());
        System.out.println((int)queue.poll());
        System.out.println((int)queue.poll());
    }
}
