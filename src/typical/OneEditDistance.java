package typical;

public class OneEditDistance {
    public boolean isOneEditDistance(String s, String t) {
        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (s.length() == t.length()) {
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else if (s.length() > t.length()) {
                    return s.substring(i + 1).equals(t.substring(i));
                } else {
                    return s.substring(i).equals(t.substring(i + 1));
                }
            }
        }

        return Math.abs(s.length() - t.length()) == 1;
    }

    public static void main(String[] args) {
        OneEditDistance ed = new OneEditDistance();
        System.out.println("edit distance = " + ed.isOneEditDistance("aDb", "adb"));
        System.out.println("s.substring = " + "aDb".substring(2));
        System.out.println("t.substring = " + "adb".substring(2));

    }
}
