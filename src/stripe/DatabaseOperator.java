package stripe;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseOperator {
    /**
     * Step 1: implement min_by_key
     */
    public static Map<String, Integer> minByKey(String key, List<Map<String, Integer>> records) {
        Map<String, Integer> res = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (Map<String, Integer> record : records) {
            int curValue = record.getOrDefault(key, 0);
            if (curValue < min) {
                min = curValue;
                res = record;
            }
        }

        return res;
    }

    /**
     * Step 2.a: implement first_by_key
     */
    public static Map<String, Integer> firstByKey(String key, List<Map<String, Integer>> records, String direction) {
        Map<String, Integer> res = new HashMap<>();
        boolean isAsc = direction.equals("asc");
        int resValue = isAsc? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (Map<String, Integer> record : records) {
            int curValue = record.getOrDefault(key, 0);
            if (shouldUpdate(isAsc, curValue, resValue)) {
                resValue = curValue;
                res = record;
            }
        }

        return res;
    }

    private static boolean shouldUpdate(boolean isAsc, int curValue, int resValue) {
        return isAsc? (curValue < resValue) : (curValue > resValue);
    }

    /**
     * Step 2.b: implement step 1 using this function
     */
    public static Map<String, Integer> minByKey2(String key, List<Map<String, Integer>> records) {
        return firstByKey(key, records, "asc");
    }

    /**
     * Step 3.b: implement step 2 using this comparator
     */
    public static Map<String, Integer> firstByKey2(String key, List<Map<String, Integer>> records, String direction) {
        boolean isAsc = direction.equals("asc");
        Collections.sort(records, new RecordComparator(key, isAsc));

        return records.get(0);
    }

    /**
     * Step 4: multiple order precedence in a comparator, find first_by_order
     */
    public static Map<String, Integer> firstBySortOrder(LinkedHashMap<String, String> sortOrder,
                                                        List<Map<String, Integer>> records) {
        Collections.sort(records, new SortOrderComparator(sortOrder));
        return records.get(0);
    }

    public static Map<String, Integer> firstByKey3(String key, List<Map<String, Integer>> records, String direction) {
        LinkedHashMap<String, String> sortOrder = SortOrder.ordered(key, direction);
        return firstBySortOrder(sortOrder, records);
    }

    public static <T> void assertEqual(T expected, T actual) {
        if (expected == null && actual == null || actual != null && actual.equals(expected)) {
            System.out.println("PASSED");
        } else {
            throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
        }
    }

    public static void main(String[] args) {
        // Step 1
        System.out.println("Step 1: minByKey");
        testMinByKey();

        // Step 2a
        System.out.println("Step 2: firstByKey");
        System.out.println("2a");
        testFirstByKey2a();

        // Step 2b
        System.out.println("2b");
        testMinByKey2();

        // Step 3
        System.out.println("Step 3: firstByKey2 with comparator");
        testFirstByKey2();

        // Step 4
        System.out.println("Step 4: firstBySortOrder with comparator");
        testFirstBySortOrder();

        // Step 5
        System.out.println("Step 5: firstByKey3 with firstBySortOrder");
        testFirstByKey3();

    }

    public static void testMinByKey() {
        List<Map<String, Integer>> example1 = Arrays.asList(
                Records.of("a", 1, "b", 2),
                Records.of("a", 2)
        );
        List<Map<String, Integer>> example2 = Arrays.asList(example1.get(1), example1.get(0));
        List<Map<String, Integer>> example3 = Arrays.asList(Records.of());
        List<Map<String, Integer>> example4 = Arrays.asList(
                Records.of("c", -1),
                Records.of("b", -1)
        );
        List<Map<String, Integer>> example5 = Arrays.asList(
                Records.of("a", 1),
                Records.of("b", -1, "a", 1)
        );

        assertEqual(example1.get(0), minByKey("a", example1));
        assertEqual(example2.get(1), minByKey("a", example2));
        assertEqual(example1.get(1), minByKey("b", example1));
        assertEqual(example3.get(0), minByKey("a", example3));
        assertEqual(example4.get(1), minByKey("b", example4));
        assertEqual(example5.get(0), minByKey("a", example5));
    }

    public static void testFirstByKey2a() {
        List<Map<String, Integer>> example1 = Arrays.asList(Records.of("a", 1));
        List<Map<String, Integer>> example2 = Arrays.asList(
                Records.of("b", 1),
                Records.of("b", -2),
                Records.of("a", 10)
        );
        List<Map<String, Integer>> example3 = Arrays.asList(
                Records.of(),
                Records.of("a", 10, "b", -10),
                Records.of(),
                Records.of("a", 3, "c", 3)
        );

        assertEqual(example1.get(0), firstByKey("a", example1, "asc"));
        assertEqual(example2.get(0), firstByKey("a", example2, "asc"));  // example2.get(1) ok too
        assertEqual(example2.get(2), firstByKey("a", example2, "desc"));
        assertEqual(example2.get(1), firstByKey("b",  example2, "asc"));
        assertEqual(example2.get(0), firstByKey("b", example2, "desc"));
        assertEqual(example3.get(1), firstByKey("a", example3, "desc"));
    }

    public static void testMinByKey2() {
        List<Map<String, Integer>> example1 = Arrays.asList(
                Records.of("a", 1, "b", 2),
                Records.of("a", 2)
        );
        List<Map<String, Integer>> example2 = Arrays.asList(example1.get(1), example1.get(0));
        List<Map<String, Integer>> example3 = Arrays.asList(Records.of());
        List<Map<String, Integer>> example4 = Arrays.asList(
                Records.of("c", -1),
                Records.of("b", -1)
        );
        List<Map<String, Integer>> example5 = Arrays.asList(
                Records.of("a", 1),
                Records.of("b", -1, "a", 1)
        );

        assertEqual(minByKey2("a", example1), minByKey("a", example1));
        assertEqual(minByKey2("a", example2), minByKey("a", example2));
        assertEqual(minByKey2("b", example1), minByKey("b", example1));
        assertEqual(minByKey2("a", example3), minByKey("a", example3));
        assertEqual(minByKey2("b", example4), minByKey("b", example4));
        assertEqual(minByKey2("a", example5), minByKey("a", example5));
    }

    public static void testFirstByKey2() {
        List<Map<String, Integer>> example1 = Arrays.asList(Records.of("a", 1));
        List<Map<String, Integer>> example2 = Arrays.asList(
                Records.of("b", 1),
                Records.of("b", -2),
                Records.of("a", 10)
        );
        List<Map<String, Integer>> example3 = Arrays.asList(
                Records.of(),
                Records.of("a", 10, "b", -10),
                Records.of(),
                Records.of("a", 3, "c", 3)
        );

        assertEqual(firstByKey2("a", example1, "asc"), firstByKey("a", example1, "asc"));
        assertEqual(firstByKey2("a", example2, "asc"), firstByKey("a", example2, "asc"));  // example2.get(1) ok too
        assertEqual(firstByKey2("a", example2, "desc"), firstByKey("a", example2, "desc"));
        assertEqual(firstByKey2("b",  example2, "asc"), firstByKey("b",  example2, "asc"));
        assertEqual(firstByKey2("b", example2, "desc"), firstByKey("b", example2, "desc"));
        assertEqual(firstByKey2("a", example3, "desc"), firstByKey("a", example3, "desc"));
    }

    public static void testFirstBySortOrder() {
        List<Map<String, Integer>> example1 = Arrays.asList(
                Records.of("a", 5),
                Records.of("a", 6)
        );
        LinkedHashMap<String, String>  order1 = SortOrder.ordered("a", "desc");

        List<Map<String, Integer>> example2 = Arrays.asList(
                Records.of("a", -5, "b", 10),
                Records.of("a", -4, "b", 9)
        );
        LinkedHashMap<String, String>  order2 = SortOrder.ordered("b", "asc", "a", "asc");

        List<Map<String, Integer>> example3 = Arrays.asList(
                Records.of("a", -5, "b", 10),
                Records.of("a", -4, "b", 10)
        );
        LinkedHashMap<String, String> order3 = SortOrder.ordered("b", "asc", "a", "asc");

        List<Map<String, Integer>> example4 = Arrays.asList(
                Records.of("a", -5, "b", 10, "c", 5),
                Records.of("a", -4, "b", 10, "c", 5),
                Records.of("a",-6, "b", 10, "c", 5)
        );
        LinkedHashMap<String, String> order4 = SortOrder.ordered("c", "asc", "b", "asc", "a", "desc");

        assertEqual(example1.get(1), firstBySortOrder(order1, example1));
        assertEqual(example2.get(1), firstBySortOrder(order2, example2));
        assertEqual(example3.get(0), firstBySortOrder(order3, example3));
        assertEqual(example4.get(1), firstBySortOrder(order4, example4));
    }

    public static void testFirstByKey3() {
        List<Map<String, Integer>> example1 = Arrays.asList(Records.of("a", 1));
        List<Map<String, Integer>> example2 = Arrays.asList(
                Records.of("b", 1),
                Records.of("b", -2),
                Records.of("a", 10)
        );
        List<Map<String, Integer>> example3 = Arrays.asList(
                Records.of(),
                Records.of("a", 10, "b", -10),
                Records.of(),
                Records.of("a", 3, "c", 3)
        );

        assertEqual(firstByKey3("a", example1, "asc"), firstByKey("a", example1, "asc"));
        assertEqual(firstByKey3("a", example2, "asc"), firstByKey("a", example2, "asc"));  // example2.get(1) ok too
        assertEqual(firstByKey3("a", example2, "desc"), firstByKey("a", example2, "desc"));
        assertEqual(firstByKey3("b",  example2, "asc"), firstByKey("b",  example2, "asc"));
        assertEqual(firstByKey3("b", example2, "desc"), firstByKey("b", example2, "desc"));
        assertEqual(firstByKey3("a", example3, "desc"), firstByKey("a", example3, "desc"));
    }
}

class Records {
    public static <K, V> Map<K, V> of() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }
}

class SortOrder {
    public static <K, V> LinkedHashMap<K, V> ordered(K k1, V v1) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> LinkedHashMap<K, V> ordered(K k1, V v1, K k2, V v2) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> LinkedHashMap<K, V> ordered(K k1, V v1, K k2, V v2, K k3, V v3) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }
}
