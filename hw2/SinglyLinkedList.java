/**
 * Your implementation of a circular singly linked list.
 * @author Abdullojon Yusupov
 * @userid abdullojony
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (data == null) throw new IllegalArgumentException();

        if (size == 0) {
            head = new LinkedListNode<>(data);
            head.setNext(head);
        } else {
            LinkedListNode<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            LinkedListNode<T> newNode = new LinkedListNode<>(node.getData(), node.getNext());
            node.setData(data);
            node.setNext(newNode);
            if (index == size) {
                head = newNode;
            }
        }

        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        LinkedListNode<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        T data = node.getData();
        if (size == 1) {
            head = null;
        } else {
            node.setData(node.getNext().getData());
            node.setNext(node.getNext().getNext());
        }

        if (--size == index) head = node;
        return data;
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        if (data == null) throw new IllegalArgumentException();

        LinkedListNode<T> node = head;
        int j = -1;
        for (int i = 0; i < size; i++) {
            if (node.getData().equals(data)) j = i;
            node = node.getNext();
        }

        if (j != -1) return removeAtIndex(j);
        else return null;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        LinkedListNode<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        LinkedListNode<T> node = head;
        T[] array = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = node.getData();
            node = node.getNext();
        }
        return array;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}