package itipxnozakki.com.lib.data.parsedata;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private List<Tree> trees = new ArrayList<Tree>();
    private List<Leaf> leafs= new ArrayList<Leaf>();
    private String name;

    public Tree(String name) {
        this.name = name;
    }
    public void setTree(Tree tree) {
        this.trees.add(tree);
    }

    public List<Tree> getTree() {
        return this.trees;
    }

    public void setLeaf(Leaf leaf) {
        this.leafs.add(leaf);
    }

    public List<Leaf> getLeaf() {
        return this.leafs;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
