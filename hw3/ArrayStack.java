import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed stack.
 *
 * @author Abdullojon Yusupov
 * @userid abdullojony
 * @version 1.0
 */
public class ArrayStack<T> {
    
    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of a stack with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length.
     *
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if(data == null) throw new IllegalArgumentException();

        if(size == backingArray.length) {
            resizeArray(size * 2);
        }

        backingArray[size++] = data;
    }

    /**
     * Resizes array to the given capacity.
     *
     * @param capacity the new capacity of the array.
     */
    private void resizeArray(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * Do not shrink the backing array.
     *
     * You should replace any spots that you pop from with null. Failure to do
     * so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException();
        T tmp = peek();
        backingArray[--size] = null;
        return tmp;
    }

    /**
     * Retrieves the next element to be popped without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() { 
        if (isEmpty()) return null;
        return backingArray[size - 1]; 
    }

    /**
     * Returns a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    private boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
    
}
