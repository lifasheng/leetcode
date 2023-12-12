/*
Hard
Design a data structure that simulates an in-memory file system.

Implement the FileSystem class:

FileSystem() Initializes the object of the system.
List<String> ls(String path)
If path is a file path, returns a list that only contains this file's name.
If path is a directory path, returns the list of file and directory names in this directory.
The answer should in lexicographic order.
void mkdir(String path) Makes a new directory according to the given path. The given directory path does not exist. If the middle directories in the path do not exist, you should create them as well.
void addContentToFile(String filePath, String content)
If filePath does not exist, creates that file containing given content.
If filePath already exists, appends the given content to original content.
String readContentFromFile(String filePath) Returns the content in the file at filePath.

Example 1:
Input
["FileSystem", "ls", "mkdir", "addContentToFile", "ls", "readContentFromFile"]
[[], ["/"], ["/a/b/c"], ["/a/b/c/d", "hello"], ["/"], ["/a/b/c/d"]]
Output
[null, [], null, null, ["a"], "hello"]

Explanation
FileSystem fileSystem = new FileSystem();
fileSystem.ls("/");                         // return []
fileSystem.mkdir("/a/b/c");
fileSystem.addContentToFile("/a/b/c/d", "hello");
fileSystem.ls("/");                         // return ["a"]
fileSystem.readContentFromFile("/a/b/c/d"); // return "hello"
 
Constraints:
1 <= path.length, filePath.length <= 100
path and filePath are absolute paths which begin with '/' and do not end with '/' except that the path is just "/".
You can assume that all directory names and file names only contain lowercase letters, and the same names will not exist in the same directory.
You can assume that all operations will be passed valid parameters, and users will not attempt to retrieve file content or list a directory or file that does not exist.
1 <= content.length <= 50
At most 300 calls will be made to ls, mkdir, addContentToFile, and readContentFromFile.
*/

public class FileSystem {
    class File {
        boolean isfile = false;
        HashMap < String, File > files = new HashMap < > ();
        String content = "";
    }
    File root;
    public FileSystem() {
        root = new File();
    }

    public List < String > ls(String path) {
        File t = root;
        List < String > files = new ArrayList < > ();
        if (!path.equals("/")) {
            String[] d = path.split("/");
            for (int i = 1; i < d.length; i++) {
                t = t.files.get(d[i]);
            }
            if (t.isfile) {
                files.add(d[d.length - 1]);
                return files;
            }
        }
        List < String > res_files = new ArrayList < > (t.files.keySet());
        Collections.sort(res_files);
        return res_files;
    }

    public void mkdir(String path) {
        File t = root;
        String[] d = path.split("/");
        for (int i = 1; i < d.length; i++) {
            if (!t.files.containsKey(d[i]))
                t.files.put(d[i], new File());
            t = t.files.get(d[i]);
        }
    }

    public void addContentToFile(String filePath, String content) {
        File t = root;
        String[] d = filePath.split("/");
        for (int i = 1; i < d.length - 1; i++) {
            t = t.files.get(d[i]);
        }
        if (!t.files.containsKey(d[d.length - 1]))
            t.files.put(d[d.length - 1], new File());
        t = t.files.get(d[d.length - 1]);
        t.isfile = true;
        t.content = t.content + content;
    }

    public String readContentFromFile(String filePath) {
        File t = root;
        String[] d = filePath.split("/");
        for (int i = 1; i < d.length - 1; i++) {
            t = t.files.get(d[i]);
        }
        return t.files.get(d[d.length - 1]).content;
    }
}
// class FileSystem {
//     class File {
//         private String path;
//         private String content;
        
//         private boolean isDir;
//         private List<File> files;
        
//         public File(String path) {
//             this.path = path;
//             files = new ArrayList<>();
//         }
        
//         public String getBaseName() {
//             String[] parts = path.split("/");
//             return parts[parts.length - 1];
//         }
        
//         public File(String path, boolean isDir) {
//             this(path);
//             this.isDir = isDir;
//         }
        
//         public boolean isDir() {
//             return this.isDir;
//         }
        
//         public void setContent(String content) {
//             this.content = content;
//         }
        
//         public String getContent() {
//             return this.content;
//         }
        
//         public void addFile(File file) {
//             files.add(file);
//         }
        
//         public List<File> getFiles() {
//             return files;
//         }
//     }
    
//     private static final String SPLITER = "/";
//     private Map<String, File> fileMap;
//     public FileSystem() {
//         fileMap = new HashMap<>();
//         fileMap.put(SPLITER, new File(SPLITER, true));
//     }
    
//     public List<String> ls(String path) {
//         List<String> result = new ArrayList<>();
//         if (fileMap.containsKey(path)) {
//             File file = fileMap.get(path);
//             if (file.isDir()) {
//                 for (File f : file.getFiles()) {
//                     result.add(f.getBaseName());
//                 }
//             } else {
//                 result.add(file.getBaseName());
//             }
//         }
//         Collections.sort(result);
//         return result;
//     }
    
//     public void mkdir(String path) {
//         if (fileMap.containsKey(path)) {
//             return;
//         }
        
//         String[] parts = path.split(SPLITER);
        
//         StringBuilder sb = new StringBuilder();
//         String prePath = SPLITER;
//         for (int i = 1; i < parts.length - 1; ++i) {
//             sb.append(SPLITER).append(parts[i]);
//             String curPath = sb.toString();
//             if (!fileMap.containsKey(curPath)) {
//                 File file = new File(curPath, true);
//                 fileMap.put(curPath, file);
//                 fileMap.get(prePath).addFile(file);
//             } 
//             prePath = curPath;
//         }
//         sb.append(SPLITER).append(parts[parts.length - 1]);
//         String curPath = sb.toString();
//         File file = new File(curPath, true);
//         fileMap.put(curPath, file);
//         fileMap.get(prePath).addFile(file);
//     }
    
//     public void addContentToFile(String filePath, String content) {
//         if (fileMap.containsKey(filePath)) {
//             File file = fileMap.get(filePath);
//             if (!file.isDir()) {
//                 file.setContent(file.getContent() + content);
//             }
//             return;
//         }
        
//         String[] parts = filePath.split(SPLITER);
//         String[] dirPathParts = new String[parts.length - 1];
//         for (int i = 0; i < parts.length - 1; ++i) {
//             dirPathParts[i] = parts[i];
//         }
        
//         // when dirPathParts == "", then join will return "";
//         String dirPath = String.join(SPLITER, dirPathParts);
//         if (dirPath.equals("")) {
//             dirPath = SPLITER;
//         }
//         mkdir(dirPath);
//         File file = new File(filePath);
//         fileMap.put(filePath, file);
//         fileMap.get(dirPath).addFile(file);
//         fileMap.get(filePath).setContent(content);
//     }
    
//     public String readContentFromFile(String filePath) {
//         return fileMap.get(filePath).getContent();
//     }
// }

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */



class FileSystem {

    class TreeNode {
        String path;
        boolean isDir;
        StringBuilder content;
        Map<String, TreeNode> children;

        public TreeNode(String path) {
            this(path, false);
        }

        public TreeNode(String path, boolean isDir) {
            this.path = path;
            this.isDir = isDir;
            this.children = new HashMap<>();
            content = new StringBuilder();
        }
    }

    TreeNode root;


    public FileSystem() {
        root = new TreeNode("/");
    }

    public List<String> ls(String path) {
        TreeNode node = root;
        if (path.equals("/")) {
            List<String> res = new ArrayList<>(node.children.keySet());
            Collections.sort(res);
            return res;
        }

        String[] dirs = path.split("/");
        for (int i = 1; i < dirs.length; ++i) {
            String dir = dirs[i];
            if (!node.children.containsKey(dir)) {
                return new ArrayList<>();
            } else {
                node = node.children.get(dir);
            }
        }
        if (node.isDir) {
            List<String> res = new ArrayList<>(node.children.keySet());
            Collections.sort(res);
            return res;
        } else {
            return List.of(node.path);
        }
    }

    public void mkdir(String path) {
        navigateTree(path);
    }

    private TreeNode navigateTree(String path) {
        TreeNode node = root;
        String[] dirs = path.split("/");
        for (int i = 1; i < dirs.length; ++i) {
            String dir = dirs[i];
            if (!node.children.containsKey(dir)) {
                node.children.put(dir, new TreeNode(dir, true));
            }
            node = node.children.get(dir);
        }
        return node;
    }

    public void addContentToFile(String filePath, String content) {
        TreeNode node = navigateTree(filePath);
        node.isDir = false;
        node.content.append(content);
    }

    public String readContentFromFile(String filePath) {
        TreeNode node = navigateTree(filePath);
        return node.content.toString();
    }
}


