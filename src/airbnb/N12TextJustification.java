package airbnb;

import java.util.ArrayList;
import java.util.List;

public class N12TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {

        List<String> res = new ArrayList<>();
        int index = 0;

        // 1 loop is 1 line
        while (index < words.length) {
            int count = words[index].length();
            int lastIndex = index + 1;

            // count how many words can be put in this line
            while (lastIndex < words.length) {
                if (count + words[lastIndex].length() + 1 > maxWidth) {
                    break;
                }
                count += words[lastIndex++].length() + 1;
            }

            // append the word at the index
            StringBuilder sb = new StringBuilder();
            sb.append(words[index]);
            int diff = lastIndex - index - 1;

            // if it is the last line
            if (lastIndex == words.length || diff == 0) {
                for (int i = index + 1; i < lastIndex; i++) {
                    sb.append(" ");
                    sb.append(words[i]);
                }

                for (int i = sb.length(); i < maxWidth; i++) {
                    sb.append(" ");
                }

                // not the last line
            } else {
                // spaces evenly after every word
                int spaces = (maxWidth - count) / diff;
                // spaces remaining
                int remain = (maxWidth - count) % diff;
                for (int i = index + 1; i < lastIndex; i++) {
                    for (int j = spaces; j > 0; j--) {
                        sb.append(" ");
                    }

                    if (remain > 0) {
                        sb.append(" ");
                        remain--;
                    }

                    sb.append(" ");
                    sb.append(words[i]);
                }
            }

            res.add(sb.toString());
            index = lastIndex;
        }

        return res;
    }
}
