import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Abdullojon Yusupov
 * @userid abdullojony
 * @version 1.0
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
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        };
        if (data.compareTo(node.getData()) > 0) { node.setRight(addHelper(data, node.getRight())); }
        else if (data.compareTo(node.getData()) < 0) { node.setLeft(addHelper(data, node.getLeft())); }
        else { return node; }

        return balance(node);
    }

    /**
     * Balances the node by rotation
     *
     * @param node the node of a tree that will get balanced
     * @return node that is balanced
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        update(node);
        int factor = node.getBalanceFactor();
        if (factor > 1) {
            int factorLeft = node.getLeft().getBalanceFactor();
            if (factorLeft < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        } else if (factor < -1) {
            int factorRight = node.getRight().getBalanceFactor();
            if (factorRight > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Do left rotation
     *
     * @param node the node that will get rotated
     * @return node that is left-rotated
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        update(node);
        update(rightNode);
        return rightNode;
    }

    /**
     * Do right rotation
     *
     * @param node the node that will get rotated
     * @return node that is right-rotated
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        update(node);
        update(leftNode);
        return leftNode;
    }

    /**
     * Updates the height and the balance factor of the node.
     *
     * @param node the node of a tree that will get updated.
     */
    private void update(AVLNode<T> node) {
        int hLeft = height(node.getLeft());
        int hRight = height(node.getRight());
        node.setHeight(Math.max(hLeft, hRight) + 1);
        node.setBalanceFactor(hLeft - hRight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) throw new IllegalArgumentException();
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    /**
     * Helper method for remove(T data)
     *
     * @param data the data to search for
     * @param node the root node to inspect
     * @param removed the node that will store removed data
     * @return parent node of node that will get removed
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) throw new NoSuchElementException();
        if (data.compareTo(node.getData()) > 0) { node.setRight(removeHelper(data, node.getRight(), removed)); }
        else if (data.compareTo(node.getData()) < 0) { node.setLeft(removeHelper(data, node.getLeft(), removed)); }
        else {
            size--;
            removed.setData(node.getData());
            if (node.getLeft() == null) { return node.getRight(); }
            else if (node.getRight() == null) { return node.getLeft(); }
            else {
                AVLNode<T> child = new AVLNode<>(null);
                node.setRight(successorHelper(node.getRight(), child));
                node.setData(child.getData());
            }
        }

        return balance(node);
    }

    /**
     * Finds successor of node
     *
     * @param node the node to inspect
     * @param child the child of a node that will be removed
     * @return successor node of an element that will be removed
     */
    private AVLNode<T> successorHelper(AVLNode<T> node, AVLNode<T> child) {
        if (node.getLeft() == null) {
            child.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(successorHelper(node.getLeft(), child));
        return balance(node);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) throw new IllegalArgumentException();
        return getHelper(data, root);
    }

    /**
     * Helper method for get(T data)
     *
     * @param data the data to search for
     * @param node the root node to inspect
     * @return data of a node that matches passed in parameter
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) throw new NoSuchElementException();
        if (data.compareTo(node.getData()) > 0) { return getHelper(data, node.getRight()); }
        if (data.compareTo(node.getData()) < 0) { return getHelper(data, node.getLeft()); }
        return node.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch(NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>(size);
        deepestBranchesHelper(list, root);
        return list;
    }

    /**
     * Helper method for deepestBranches()
     *
     * @param list the list that data will added
     * @param node the root node to inspect
     */
    private void deepestBranchesHelper(List<T> list, AVLNode<T> node) {
        if (node != null) {
            list.add(node.getData());
            if (node.getLeft() != null && node.getLeft().getHeight() + 1 == node.getHeight()) deepestBranchesHelper(list, node.getLeft());
            if (node.getRight() != null && node.getRight().getHeight() + 1 == node.getHeight()) deepestBranchesHelper(list, node.getRight());
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException();
        }
        List<T> list = new ArrayList<>();
        if (!data1.equals(data2)) sortedInBetweenHelper(data1, data2, root, list);
        return list;
    }

    /**
     * Helper method for sortedInBetween(T data1, T data2)
     *
     * @param data1 first boundary of data
     * @param data2 second boundary of data
     * @param list the list that data will added
     * @param node the root node to inspect
     */
    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> node, List<T> list) {
        if (node != null) {
            if (data1.compareTo(node.getData()) < 0) sortedInBetweenHelper(data1, data2, node.getLeft(), list);
            if (node.getData().compareTo(data1) > 0 
            && node.getData().compareTo(data2) < 0) { list.add(node.getData()); }
            if (node.getData().compareTo(data2) < 0) sortedInBetweenHelper(data1, data2, node.getRight(), list);
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Returns height of a specific node
     *
     * @param node the node to inspect
     * @return the height of a node
     */
    public int height(AVLNode<T> node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
}
