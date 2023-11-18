import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author [Your Name]
 * @userid [Your User ID]
 * @version [Version Number]
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        // [Implement this constructor]
        if (data == null) throw new IllegalArgumentException();
        size = 0;
        for (T d : data) { add(d); }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        // [Implement this method]
        if (data == null) throw new IllegalArgumentException();
        root = addHelper(data, root);
    }

    /**
     * Helper method for add(T data)
     *
     * @param data the data to be added
     * @param node the root of a tree
     * @return node that is balanced
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        // [Implement this method] 
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        };
        if (data.compareTo(node.getData()) > 0) { node.setRight(addHelper(data, node.getRight())); }
        else if (data.compareTo(node.getData()) < 0) { node.setLeft(addHelper(data, node.getLeft())); }
        else { return node; }

        return balance(node);
    }

    public T remove(T data) {
        if (data == null) throw new IllegalArgumentException();
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(data, root, dummy);
        return dummy.getData();
    }

    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) throw new NoSuchElementException();
        if (data.compareTo(node.getData()) > 0) { node.setRight(removeHelper(data, node.getRight(), dummy)); }
        else if (data.compareTo(node.getData()) < 0) { node.setLeft(removeHelper(data, node.getLeft(), dummy)); }
        else {
            size--;
            dummy.setData(node.getData());
            if (node.getLeft() == null) { return node.getRight(); }
            else if (node.getRight() == null) { return node.getLeft(); }
            else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                node.setRight(successor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            }
        }

        return balance(node);
    }

    private AVLNode<T> successor(AVLNode<T> node, AVLNode<T> dummy) {
        if (node.getLeft() == null) {
            dummy.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(successor(node.getLeft(), dummy));
        return balance(node);
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        updateHeightAndBF(node);
        int factor = node.getBalanceFactor();
        if (factor > 1) {
            int factorLeft = node.getLeft().getBalanceFactor();
            if (factorLeft == 0 || factorLeft == 1) return rotateRight(node);
            if (factorLeft == -1) { 
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        } else if (factor < -1) {
            int factorRight = node.getRight().getBalanceFactor();
            if (factorRight == -1 || factorRight == 0) return rotateLeft(node);
            if (factorRight == 1) {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }
        return node;
    }

    private void updateHeightAndBF(AVLNode<T> node) {
        if (node == null) throw new IllegalArgumentException();
        int hLeft = -1;
        int hRight = -1;
        if (node.getLeft() != null) { hLeft = node.getLeft().getHeight(); }
        if (node.getRight() != null) { hRight = node.getRight().getHeight(); }
        node.setHeight(Math.max(hLeft, hRight) + 1);
        node.setBalanceFactor(hLeft - hRight);
    }

    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        updateHeightAndBF(node);
        updateHeightAndBF(rightNode);
        return rightNode;
    }

    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        updateHeightAndBF(node);
        updateHeightAndBF(leftNode);
        return leftNode;
    }

    public T get(T data) {
        if (data == null) throw new IllegalArgumentException();
        return getHelper(data, root);
    }

    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) throw new NoSuchElementException();
        if (data.compareTo(node.getData()) > 0) { return getHelper(data, node.getRight()); }
        if (data.compareTo(node.getData()) < 0) { return getHelper(data, node.getLeft()); }
        return node.getData();
    }

    public boolean contains(T data) {
        try {
            get(data);
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>(size);
        deepestBranchesHelper(list, root);
        return list;
    }

    private void deepestBranchesHelper(List<T> list, AVLNode<T> node) {
        if (node != null) {
            list.add(node.getData());
            if (node.getLeft() != null && node.getLeft().getHeight() + 1 == node.getHeight()) deepestBranchesHelper(list, node.getLeft());
            if (node.getRight() != null && node.getRight().getHeight() + 1 == node.getHeight()) deepestBranchesHelper(list, node.getRight());
        }
    }

    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) throw new IllegalArgumentException();
        List<T> list = new ArrayList<>();
        if (!data1.equals(data2)) sortedInBetweenHelper(data1, data2, root, list);
        return list;
    }

    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> node, List<T> list) {
        if (node != null) {
            if (data1.compareTo(node.getData()) < 0) sortedInBetweenHelper(data1, data2, node.getLeft(), list);
            if (node.getData().compareTo(data1) > 0 
            && node.getData().compareTo(data2) < 0) { list.add(node.getData()); }
            if (node.getData().compareTo(data2) < 0) sortedInBetweenHelper(data1, data2, node.getRight(), list);
        }
    }

    public int height(AVLNode<T> node) {
        return node==null? -1 : node.getHeight();
    }

    public int height() {
        return height(root);
    }

    public int size() { return size; }
    public AVLNode<T> getRoot() { return root; }

    public void clear() {
        root = null;
        size = 0;
    }
}
