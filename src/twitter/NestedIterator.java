package twitter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {
    // first add all NestedInteger to the deque
    // when calling hasNext() if there is list, we process the NestedIterator
    Deque<NestedInteger> deque;

    public NestedIterator(List<NestedInteger> nestedList) {
        deque = new ArrayDeque<>();
        for (NestedInteger n : nestedList) {
            deque.offerLast(n);
        }
    }

    @Override
    public boolean hasNext() {
        // if the peek (next) is a list, we process that list when hasNext()
        while (!deque.isEmpty() && !deque.peekFirst().isInteger()) {
            List<NestedInteger> list = deque.pollFirst().getList();
            // add from last to first
            for (int i = list.size() - 1; i >= 0; i--) {
                deque.offerFirst(list.get(i));
            }
        }
        return !deque.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) return null;
        return deque.pollFirst().getInteger();
    }
}

class NestedInteger {
    Integer n;
    List<NestedInteger> list;
    NestedInteger(Integer n) {
        this.n = n;
        list = null;
    }

    NestedInteger(List<NestedInteger> list) {
        this.list = list;
        n = null;
    }

    public boolean isInteger() {
        return n == null;
    }

    public Integer getInteger() {
        return n;
    }

    public List<NestedInteger> getList() {
        return list;
    }
}
