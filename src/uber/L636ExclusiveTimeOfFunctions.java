package uber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class L636ExclusiveTimeOfFunctions {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];

        Stack<Log> stack = new Stack<>();
        for (String log : logs) {
            Log item = new Log(log);
            if (item.isStart) {
                stack.push(item);
            } else {
                Log top = stack.pop();
                res[item.func] += item.time - top.time + 1;

                if (!stack.isEmpty()) {
                    res[stack.peek().func] -= item.time - top.time + 1;
                }
            }
        }

        return res;
    }

    class Log {
        int func;
        boolean isStart;
        int time;

        Log(String log) {
            String[] logArr = log.split(":");
            func = Integer.valueOf(logArr[0]);
            isStart = logArr[1].equals("start");
            time = Integer.valueOf(logArr[2]);
        }
    }

    public static void main(String[] args) {
        String[] stringArr = {"0:start:0","1:start:2","1:end:5","0:end:6"};
        List<String> logs = new ArrayList<>(Arrays.asList(stringArr));
        L636ExclusiveTimeOfFunctions s = new L636ExclusiveTimeOfFunctions();
        System.out.println(Arrays.toString(s.exclusiveTime(2, logs)));
    }
}
