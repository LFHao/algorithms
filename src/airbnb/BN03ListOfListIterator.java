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
        while (colIter == null && rowIter.hasNext()) {
            colIter = rowIter.next().iterator();
        }
        if (colIter != null) {
            colIter.remove();
        }
    }

    public static void main(String[] args) {
        List<Integer> array1 = new ArrayList<>(Arrays.asList(1, 2));
        List<Integer> array2 = Arrays.asList(3);
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(array1);
        lists.add(array2);

        Iterator a = array1.iterator();
        a.hasNext();
        a.next();
        a.remove();

//        airbnb.BN03ListOfListIterator aaa = new airbnb.BN03ListOfListIterator(lists);
//        aaa.hasNext();
//        aaa.next();
//        aaa.remove();
//        System.out.println(iterator.next());
//        System.out.println(aaa.hasNext());
//        System.out.println(aaa.next());
//        System.out.println(aaa.hasNext());
//        System.out.println(aaa.next());
//        aaa.remove();
//        System.out.println(lists);

    }
}
