import java.util.NoSuchElementException;

public class ArrayQueue<T> {
    private T[] array;
    private int size;
    private int f;

    public static final int INITIAL_CAPACITY = 9;

    ArrayQueue() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        f = 0;
        size = 0;
    }

    public void enqueue(T data) {
        if (data == null) throw new IllegalArgumentException();
        if (size == array.length) {
            T[] newArray = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                int r = (f + i) % size;
                newArray[i] = array[r];
            }
            array = newArray;
            f = 0;
        }
        int r = (f + size) % array.length;
        array[r] = data;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        T tmp = peek();
        array[f] = null;
        f = (f + 1) % array.length;
        if (--size == 0) f = 0;
        return tmp;
    }

    public T peek() {
        if(isEmpty()) return null;
        return array[f];
    }

    public int size() { return size; }

    public Object[] getBackingArray() { return array; }

    public boolean isEmpty() { return size == 0; }


}
