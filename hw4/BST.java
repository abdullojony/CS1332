import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;

/**
 * Your implementation of a binary search tree.
 *
 * @author Hwuiwon Kim
 * @userid hkim944
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    private BSTNode<T> root;
    private int size;

    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) throw new IllegalArgumentException();
        size = 0;
        for (T entry : data) {
            add(entry);
        }
    }

    // METHOD 1
    // public void add(T data) {
    //     if (data == null) throw new IllegalArgumentException();
    //     if (root == null) {
    //         root = new BSTNode<>(data);
    //         size++;
    //     }
    //     else addHelper(data, root);
    // }

    // private void addHelper(T data, BSTNode<T> node) {
    //     if (data.compareTo(node.getData()) > 0) {
    //         if (node.getRight() != null) { addHelper(data, node.getRight()); }
    //         else { node.setRight(new BSTNode<>(data)); size++; }
    //     }
    //     else if (data.compareTo(node.getData()) < 0) {
    //         if (node.getLeft() != null) { addHelper(data, node.getLeft()); }
    //         else { node.setLeft(new BSTNode<>(data)); size++; }
    //     }
    // }

    // METHOD 2 (pointer reinforcement)
    public void add(T data) {
        if (data == null) throw new IllegalArgumentException();
        root = addHelper(data, root);
    }

    private BSTNode<T> addHelper(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        }
        if (data.compareTo(node.getData()) > 0) { node.setRight(addHelper(data, node.getRight())); }
        else if (data.compareTo(node.getData()) < 0) { node.setLeft(addHelper(data, node.getLeft())); }

        return node;
    }

    public T remove(T data) {
        if (data == null) throw new IllegalArgumentException();
        BSTNode<T> dummy1 = new BSTNode<>(null);
        root = removeHelper(data, root, dummy1);
        return dummy1.getData();
    }

    private BSTNode<T> removeHelper(T data, BSTNode<T> node, BSTNode<T> removed) {
      if (node == null) throw new NoSuchElementException();
      if (data.compareTo(node.getData()) > 0) { node.setRight(removeHelper(data, node.getRight(), removed)); }
      else if (data.compareTo(node.getData()) < 0) { node.setLeft(removeHelper(data, node.getLeft(), removed)); }
      else {
        removed.setData(node.getData());
        size--;
        if (node.getLeft() == null) { return node.getRight(); }
        else if (node.getRight() == null) { return node.getLeft(); }
        else {
            BSTNode<T> dummy2 = new BSTNode<>(null);
            node.setLeft(predecessorHelper(node.getLeft(), dummy2));
            node.setData(dummy2.getData());
        }
      }

      return node;
    }

    private BSTNode<T> predecessorHelper(BSTNode<T> node, BSTNode<T> child) {
        if (node.getRight() == null) {
            child.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(predecessorHelper(node.getRight(), child));
        return node;
    }

    public T get(T data) {
        if (data == null) throw new IllegalArgumentException();
        return getHelper(data, root);
    }

    private T getHelper(T data, BSTNode<T> node) {
        if (node == null) throw new NoSuchElementException();
        if (data.compareTo(node.getData()) > 0) { return getHelper(data, node.getRight()); }
        else if (data.compareTo(node.getData()) < 0) { return getHelper(data, node.getLeft()); }
        else { return data; }
    }

    public boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public List<T> preorder() {
        List<T> ls = new LinkedList<>();
        preorderHelper(ls, root);
        return ls;
    }

    private void preorderHelper(List<T> list, BSTNode<T> node) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(list, node.getLeft());
            preorderHelper(list, node.getRight());
        }
    }

    public List<T> inorder() {
        List<T> ls = new LinkedList<>();
        inorderHelper(ls, root);
        return ls;
    }

    private void inorderHelper(List<T> list, BSTNode<T> node) {
        if (node != null) {
            inorderHelper(list, node.getLeft());
            list.add(node.getData());
            inorderHelper(list, node.getRight());
        }
    }

    public List<T> postorder() {
        List<T> ls = new LinkedList<>();
        postorderHelper(ls, root);
        return ls;
    }

    private void postorderHelper(List<T> list, BSTNode<T> node) {
        if (node != null) {
            postorderHelper(list, node.getLeft());
            postorderHelper(list, node.getRight());
            list.add(node.getData());
        }
    }

    public List<T> levelorder() {
        List<T> ls = new LinkedList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.remove();
            ls.add(node.getData());
            if (node.getLeft() != null) queue.add(node.getLeft());
            if (node.getRight() != null) queue.add(node.getRight());
        }
        return ls;
    }

    public static <T extends Comparable<? super T>> boolean isBST(BSTNode<T> treeRoot) {
        return isBSTHelper(treeRoot);
    }

    private static <T extends Comparable<? super T>> boolean isBSTHelper(BSTNode<T> node) {
        if (node != null) {
            if (node.getLeft() != null) {
                if (node.getData().compareTo(node.getLeft().getData()) < 0) return false;
                return isBSTHelper(node.getLeft());
            }
            if (node.getRight() != null) {
                if (node.getData().compareTo(node.getRight().getData()) > 0) return false;
                return isBSTHelper(node.getRight());
            }
        }
        return true;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        return heightHelper(root);
    }

    public int heightHelper(BSTNode<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight()));
    }

    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
