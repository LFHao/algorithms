package airbnb;

/***
 * 1. Fields containing line breaks (CRLF), double quotes, and commas should be enclosed in double-quotes.
 * 2. If double-quotes are used to enclose fields, then a double-quote
 *        appearing inside a field must be escaped by preceding it with
 *        another double quote.  For example:
 *        "aaa","b""bb","ccc"
 *
 * 一个大妈考得，说是一般在店面考，卧轨了。
 * csv parser
 * 如果有逗号，转化成|
 * 如果有引号，不考虑引号里逗号，把引号里的内容去引号整体打印。
 * 如果有两重引号，只去掉一重引号。
 * 如果有多重引号，只留一重引号
 *
 * 例子
 * aa, bb, “aa”,”aa,bb”, “aa””aa”””
 * 输出
 * aa|bb|aa|aa,bb|aa”aa”
 *
 * 这类问题用“确定有限状态自动机”（DFA，Deterministic Finite Automaton）来解决是逻辑最清晰的
 */

public class DN11CSVParser {
    private enum State {
        FIELD_START, // a field starts
        QUOTE_LESS, // no quote in the field
        QUOTE_START, // this is the 1st quote in 1 field
        QUOTE_IN_QUOTE // this is the 2nd/3rd quote after 1st quote
    }

    public static String parseCSV(String doc) {
        StringBuilder sb = new StringBuilder();
        State state = State.FIELD_START;

        for(int i = 0; i < doc.length(); i++) {
            char c = doc.charAt(i);

            switch (state) {
                case FIELD_START:
                    if (c == '"') {
                        state = State.QUOTE_START;
                        continue;
                    }
                    // if there is no " then this field is quote-less
                    state = State.QUOTE_LESS;
                    break;
                case QUOTE_START:
                    // 1 special case to handle here is another quote appears
                    if (c == '"') {
                        state = State.QUOTE_IN_QUOTE;
                        continue;
                    }
                    break;
                case QUOTE_IN_QUOTE:
                    // if it is in QUOTE_IN_QUOTE, it means 2 quotes are found before in this field
                    switch (c) {
                        // if the 3rd quote appears, then it is the last and remaining quote, it means the right quote starts
                        case '"':
                            state = State.QUOTE_START;
                            break;
                            // the other valid char is , it means this field ends and another field will start, you need to append the |
                        case ',':
                            state = State.FIELD_START;
                            c = '|';
                            break;
                            default: Error(i, c);

                    }
                    break;
                case QUOTE_LESS:
                    // handle quote less situations
                    switch (c) {
                        case ',':
                            c = '|';
                            // SHOULD NOT have the , here because later you need to append the char
                            // return the line, a new line and field will start
                        case '\n':
                            state = State.FIELD_START;
                            break;
                            // other input is invalid
                        case '"':
                            Error(i, c);
                    }
                    break;
            }
            sb.append(c);
        }

        return sb.toString();
    }

    private static void Error(int position, char ch) {
        throw new RuntimeException(
                "Invalid character " + ch + " at position " + position);
    }

    public static void main(String[] args) {
        String doc = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0\n" +
                        "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1\n" +
                        "\"\"\"Alexandra Alex\"\"\"";
//        String doc = "\"aa\",ab";
        System.out.println(doc);

        System.out.println(DN11CSVParser.parseCSV(doc));
    }
}
