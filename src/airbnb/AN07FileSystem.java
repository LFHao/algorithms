package airbnb;

import java.util.HashMap;
import java.util.Map;

/**
 * Question:
 * create("/a",1)
 * get("/a") //得到1
 * create("/a/b",2)
 * get("/a/b") //得到2
 * create("/c/d",1) //Error，因为它的上一级“/c”并不存在
 * get("/c") //Error,因为“/c”不存在
 * follow up 是写一个watch 函数，比如watch("/a",new Runnable(){System.out.println("helloword");})
 * 后，每当create("/a/b"，1) 等在/a 之下的目录不产生error 的话，都会执行绑在“/a”上的callback
 * 函数
 * 比如 watch("/a",System.out.println("yes"))
 * watch("/a/b",System.out.println("no"))
 */
public class AN07FileSystem {
    Map<String, String> pathMap;
    Map<String, Runnable> callbackMap;

    public AN07FileSystem(Map<String, String> pathMap, Map<String, Runnable> callbackMap) {
        this.pathMap = pathMap;
        this.callbackMap = callbackMap;
    }

    public boolean create(String path, String fileName) {
        if (pathMap.containsKey(path)) {
            return false;
        }

        String parentPath = path.substring(0, path.lastIndexOf("/"));
        if (!pathMap.containsKey(parentPath)) {
            return false;
        }

        pathMap.put(path, fileName);
        callback(path);
        return true;
    }

    public boolean set(String path, String fileName) {
        if (!pathMap.containsKey(path)) {
            return false;
        }

        pathMap.put(path, fileName);
        callback(path);
        return true;
    }

    public String get(String path) {
        if (!pathMap.containsKey(path)) return "ERROR";
        return pathMap.get(path);
    }

    public boolean watch(String path, Runnable runnable) {
        if (!pathMap.containsKey(path)) {
            return false;
        }

        callbackMap.put(path, runnable);
        return true;
    }

    private void callback(String path) {
        int nextPathIndex = path.indexOf("/", path.indexOf("/") + 1);

        // print from root to last
        // such as for path: /a/b/c/d
        // it prints /a -> A then /a/b -> B then /a/b/c -> C then /a/b/c/d -> D
        while (nextPathIndex > -1) {
            String parentPath = path.substring(0, nextPathIndex);
            // if there is call back in this path, then callback
            if (callbackMap.containsKey(parentPath)) {
                callbackMap.get(parentPath).run();
            }

            if (nextPathIndex == path.lastIndexOf("/")) {
                nextPathIndex = path.length();
            } else {
                nextPathIndex = path.indexOf("/", nextPathIndex + 1);
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> pathMap = new HashMap<>();
        Map<String, Runnable> callbackMap = new HashMap<>();

        // set the root
        pathMap.put("", "");

        AN07FileSystem fs = new AN07FileSystem(pathMap, callbackMap);
        System.out.println("create /a f1 " + fs.create("/a", "f1"));
        System.out.println("fs.get(\"/a\") ->" + fs.get("/a"));

        System.out.println("create /a/b f2 " + fs.create("/a/b", "f2"));
        System.out.println("fs.get(\"/a/b\") ->" + fs.get("/a/b"));

        fs.watch("/a", () -> System.out.println("A"));
        fs.watch("/a/b", () -> System.out.println("B"));

        System.out.println(fs.create("/c/d", "f3"));
        System.out.println("fs.get(\"/c/d\") ->" + fs.get("/c/d"));

        System.out.println("create /a/b/c f3 " + fs.create("/a/b/c", "f3"));
        System.out.println("fs.get(\"/a/b/c\") ->" + fs.get("/a/b/c"));

        fs.watch("/a/b/c", () -> System.out.println("C"));

        System.out.println("set /a/b/c f4 " + fs.set("/a/b/c", "f4"));
        System.out.println("fs.get(\"/a/b/c\") ->" + fs.get("/a/b/c"));
    }

}
