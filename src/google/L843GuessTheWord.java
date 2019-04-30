package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L843GuessTheWord {
    /**
     * 当一个单词和其他单词match number为0的次数越多，那么这个单词越不好，因为match number为0时我们减少搜索空间的速度最慢。
     *
     * 假如现在有无限多长度为6的单词，对于word X，和他match number为0的单词有25^6这么多个，然而和X match number为1的单词则减少到了
     * 25^5 * 6这么多个，为2时为 C(6, 2) * 25^4，以此类推，match number越大我们下一轮的搜索空间会越小，所以这里我们每一轮都挑选出当
     * 前搜索空间中和其他单词match number为0的次数最少的单词作为guess word来猜，这样minimize了每次猜词的worse case。
     * @param wordlist
     * @param master
     */
    public void findSecretWord(String[] wordlist, Master master) {
        List<String> list = new ArrayList<>();
        for(String str: wordlist) list.add(str);

        for(int i = 0 ; i < 10 ; i++) {
            Map<String, Integer> zeroMatch = new HashMap<>();
            for(String s1: list) {
                zeroMatch.putIfAbsent(s1, 0);
                for(String s2: list) {
                    if(match(s1, s2) == 0) {
                        zeroMatch.put(s1, zeroMatch.get(s1) + 1);
                    }
                }
            }
            Pair pair = new Pair("", 101);  // list size is 100
            for(String key : zeroMatch.keySet()) {
                if(zeroMatch.get(key) < pair.freq) {
                    pair = new Pair(key, zeroMatch.get(key));
                }
            }
            int matchNum = master.guess(pair.key);
            if(matchNum == 6) return;
            List<String> tmp = new ArrayList<>();
            for(String s: list) {
                if(match(s, pair.key) == matchNum) {
                    tmp.add(s);
                }
            }
            list = tmp;
        }
    }

    private int match(String s1, String s2) {
        int res = 0;
        for(int i = 0 ; i < s1.length() ; i++) {
            if(s1.charAt(i) == s2.charAt(i)) res++;
        }
        return res;
    }

    class Pair {
        String key;
        int freq;
        public Pair(String key, int freq) {
            this.key = key;
            this.freq = freq;
        }
    }

}

interface Master {
    public int guess(String word);
}
