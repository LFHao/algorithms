package dropbox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileAccess {
    private Map<String, String> parent;
    private Set<String> access;
    private Set<String> visitedAccess;

    public FileAccess(Map<String, String> parent, Set<String> access) {
        this.parent = parent;
        this.access = access;
        this.visitedAccess = new HashSet<>();
    }

    public boolean hasAccess(String folder) {
        String curFolder = folder;
        while (curFolder != null) {
            if (access.contains(curFolder) || visitedAccess.contains(curFolder)) {
                visitedAccess.add(curFolder);
                return true;
            } else {
                curFolder = parent.get(curFolder);
            }
        }
        return false;
    }

    // follow up
    public void removeRedundantAccess() {
        Set<String> newSet = new HashSet<>();
        for (String folder : access) {
            // check if folder's parent have access
            String parentFolder = parent.get(folder);
            boolean needDelete = false;
            while (parentFolder != null && !needDelete) {
                if (access.contains(parentFolder)) {
                    needDelete = true;
                } else {
                    parentFolder = parent.get(parentFolder);
                }
            }
            if (!needDelete) newSet.add(folder);
        }
        access = newSet;
    }


    public static void main(String[] args) {
        Map<String, String> foldersParent = new HashMap<>();
        foldersParent.put("B", "A");
        foldersParent.put("C", "B");
        foldersParent.put("D", "B");
        foldersParent.put("E", "A");
        foldersParent.put("F", "E");

        Set<String> access = new HashSet<>();
        access.add("C");
        access.add("E");

        FileAccess s1 = new FileAccess(foldersParent, access);
        assert s1.hasAccess("B") == false;
        assert s1.hasAccess("C") == true;
        assert s1.hasAccess("F") == true;
        assert s1.hasAccess("G") == false; //G is not in the folders structure

        access.add("B");
        access.add("A");
        FileAccess s2 = new FileAccess(foldersParent, access);
        assert s2.access.size() == 4;
        s2.removeRedundantAccess();
        assert s2.access.size() == 2;

    }

}
