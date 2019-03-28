package airbnb;

import java.util.Stack;

public class N05BasicCalculator {
    public int calculate(String s) {
        int curr = 0;
        int res = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '+') {
                res += sign * curr;
                sign = 1;
                curr = 0;
            } else if (c == '-') {
                res += sign * curr;
                sign = -1;
                curr = 0;
            } else if (Character.isDigit(c)) {
                curr = curr * 10 + (c - '0');
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                //reset the sign and result for the value in the parenthesis
                sign = 1;
                res = 0;
            } else if (c == ')') {
                res += sign * curr;
                curr = 0;
                res *= stack.pop();
                res += stack.pop();
            }
        }
        if(curr != 0) res += sign * curr;

        return res;

    }

    public static void main(String[] args) {
        N05BasicCalculator bc = new N05BasicCalculator();

        System.out.println(bc.calculate("(1+(4+5+2)-3)+(6+8)"));
    }


}
