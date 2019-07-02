package typical;

import java.util.Stack;

public class L150PolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            char c = tokens[i].charAt(0);
            int cur = 0;
            if (!Character.isDigit(c)) {
                int first, second;
                second = stack.pop();
                first = stack.pop();

                switch(c) {
                    case '*' : cur = first * second;
                        break;
                    case '/': cur = first / second;
                        break;
                    case '+': cur = first + second;
                        break;
                    case '-': cur = first - second;
                        break;
                    default: break;
                }
            } else {
                cur = Integer.parseInt(tokens[i]);
            }

            stack.push(cur);
        }

        return stack.isEmpty() ? 0 : stack.pop();
    }

    public static void main(String[] args) {
        L150PolishNotation s = new L150PolishNotation();
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(s.evalRPN(tokens));
    }
}
