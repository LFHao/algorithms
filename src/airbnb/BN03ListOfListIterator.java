package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BN03ListOfListIterator implements Iterator<Integer> {
    Iterator<List<Integer>> rowIter;
    Iterator<Integer> colIter;

    public BN03ListOfListIterator(List<List<Integer>> lists) {
        this.rowIter = lists.iterator();
        this.colIter = Collections.emptyIterator();
    }

    private void findNextIter() {
        while ((colIter == null || !colIter.hasNext()) && rowIter.hasNext()) {
            colIter = rowIter.next().iterator();
        }
    }

    @Override
    public boolean hasNext() {
        findNextIter();
        return colIter != null && colIter.hasNext();
    }


    @Override
    public Integer next() {
        return colIter.next();
    }

    @Override
    public void remove() {
        // if !colIter.hasNext(), it.remove() should throw exception, so here we should leave it as the same for this remove()
        while (colIter == null && rowIter.hasNext()) {
            colIter = rowIter.next().iterator();
        }
        if (colIter != null) {
            colIter.remove();
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> test = new ArrayList<>();
        test.add(new ArrayList<Integer>() {{
            add(1);
            add(2);
        }});
        test.add(new ArrayList<Integer>() {{
            add(3);
        }});
        test.add(new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(6);
        }});

        BN03ListOfListIterator s = new BN03ListOfListIterator(test);
        System.out.println(s.hasNext());
        System.out.println(s.next());
        s.remove();
        List<Integer> res = new ArrayList<>();
        while (s.hasNext()) {
            res.add(s.next());
        }

        System.out.println(res);
    }
}
