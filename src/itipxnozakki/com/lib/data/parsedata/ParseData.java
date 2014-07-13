package itipxnozakki.com.lib.data.parsedata;

import java.util.ArrayList;
import java.util.List;

public class ParseData {
    private Tree dataTree;
    private List<Tree> treeList = new ArrayList<Tree>();

    public ParseData(Tree dataTree) {
        this.dataTree = dataTree;
    }

    public String getData(String key) {
        return this.get(this.dataTree, key);
    }

    public List<Tree> getDatas(String key) {
        this.get2(this.dataTree, key);
        return treeList;
    }

    public String getDataByTree(Tree tree, String key) {
        return this.get(tree, key);
    }

    private String get(Tree tree, String key) {

        if (tree.getTree().size() > 0) {
            if (tree.getLeaf().size() > 0) {
                for(Leaf list : tree.getLeaf()) {
                    if (list.getKey() == key) {
                        return list.getData();
                    }
                }
            }
            for (int i = 0; i < tree.getTree().size(); i++) {
                String resultData = this.get(tree.getTree().get(i), key);
                if(resultData != null) {
                    return resultData;
                }
            }
        } else {
            if (tree.getLeaf().size() > 0) {
                for(Leaf list : tree.getLeaf()) {
                    if (list.getKey() == key) {
                        return list.getData();
                    }
                }
            }
        }
        return null;
    }

    private void get2(Tree tree, String key) {

        if (tree.getTree().size() > 0) {
            for (int i = 0; i < tree.getTree().size(); i++) {
                if (tree.getTree().get(i).getName().equals(key)) {
                    treeList.add(tree.getTree().get(i));
                }
                this.get2(tree.getTree().get(i), key);
            }
        }
    }


    public void show() {
        this._show(this.dataTree, "");
    }

    private void _show(Tree tree, String showT) {
        System.out.println(showT + tree.getName());
        showT += "\t";
        if (tree.getTree().size() > 0) {
            if (tree.getLeaf().size() > 0) {
                for(Leaf list : tree.getLeaf()) {
                    System.out.println(showT + list.getKey() + " : " + list.getData());
                }
            }
            for (int i = 0; i < tree.getTree().size(); i++) {
                _show(tree.getTree().get(i), showT);
            }
        } else {
            if (tree.getLeaf().size() > 0) {
                for(Leaf list : tree.getLeaf()) {
                    System.out.println(showT + list.getKey() + " : " + list.getData());
                }
            }
        }
    }
}
