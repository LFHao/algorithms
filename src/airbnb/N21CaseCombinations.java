package airbnb;

import java.util.ArrayList;
import java.util.List;

public class N21CaseCombinations {
    public List<String> caseCombination(String text) {
        char[] charArr = text.toCharArray();
        List<String> res = new ArrayList<>();
        backtrack(res, new StringBuilder(), charArr, 0);
        return res;
    }

    private void backtrack(List<String> res, StringBuilder temp, char[] charArr, int i) {
        if (temp.length() == charArr.length) {
            res.add(temp.toString());
            return;
        }

        temp.append(charArr[i]);
        backtrack(res, temp, charArr, i + 1);
        temp.deleteCharAt(temp.length() - 1);

        temp.append(Character.toUpperCase(charArr[i]));
        backtrack(res, temp, charArr, i + 1);
        temp.deleteCharAt(temp.length() - 1);
    }

    public static void main(String[] args) {
        N21CaseCombinations cc = new N21CaseCombinations();
        System.out.println("res = " + cc.caseCombination("abbcd"));
    }
}
