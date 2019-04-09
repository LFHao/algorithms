package airbnb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BN33GuessNumber {
    String correct;
    BN33GuessNumber(String correct) {
        this.correct = correct;
    }

    void setCorrect(String correct) {
        this.correct = correct;
    }

    public int guessNumber() {
        Map<Integer, Integer> candidates = new HashMap<>();
        for (int k = 2; k <= 6; k++) {
            candidates.put(k, 0);
        }

        String prevGuess = "1111";
        int[] initRes = guess(prevGuess, this.correct);
        int prevFirst = initRes[0], prevSecond = initRes[1];
        candidates.put(1, prevFirst);
        int steps = 0;

        int i = 0;
        while (i < this.correct.length()) {
            for (int k : candidates.keySet()) {
                steps++;
                char[] curGuess = prevGuess.toCharArray();
                curGuess[i] = Character.forDigit(k, 10);
                String curGuessNum = new String(curGuess);
                System.out.println(curGuessNum);

                int[] guessRes = guess(curGuessNum, this.correct);
                if (guessRes[0] == 4) {
                    return steps;
                }
                if (guessRes[1] > prevSecond) {
                    candidates.put(k, candidates.get(k) + guessRes[1] - prevSecond);
                }

                if (guessRes[0] > prevFirst) {
                    candidates.put(k, candidates.get(k) + guessRes[0] - prevFirst);
                    prevGuess = curGuessNum;
                    prevFirst = guessRes[0];
                    prevSecond = guessRes[1];
                }

            }
            removeCandidates(candidates);
            i++;
        }

        return steps;

    }

    private void removeCandidates(Map<Integer, Integer> map) {
        Iterator<Map.Entry<Integer,Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,Integer> item = it.next();
            if (item.getValue() <= 0) {
                it.remove();
            }
        }
    }

    public int[] guess(String num, String correct) {
        int[] res = new int[2];
        Set<Integer> indexSet = new HashSet<>();
        Map<Character, Integer> digitMap = new HashMap<>();

        for (int i = 0; i < correct.length(); i++) {
            if (num.charAt(i) == correct.charAt(i)) {
                indexSet.add(i);
            } else {
                char digit = correct.charAt(i);
                digitMap.put((digit), digitMap.getOrDefault(digit, 0) + 1);
            }
        }

        res[0] = indexSet.size();
        int count = 0;
        for (int i = 0; i < num.length(); i++) {
            char digit = num.charAt(i);
            if (!indexSet.contains(i) && digitMap.containsKey(digit) && digitMap.get(digit) > 0) {
                digitMap.put(digit, digitMap.get(digit) - 1);
                count++;
            }
        }
        res[1] = count;

        return res;

    }

    public static void main(String[] args) {
        BN33GuessNumber s = new BN33GuessNumber("5566");
//        System.out.println(s.guessNumber());
        System.out.println(s.guess("6111", "5566")[0] + ", " + s.guess("6111", "5566")[1]);
    }
}
