import java.util.NoSuchElementException;

public class ArrayStack<T> {
    private int size;
    private T[] array;

    public static final int INITIAL_CAPACITY = 9;

    ArrayStack() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void push(T data) {
        if(data == null) throw new IllegalArgumentException();

        if(size == array.length) {
            T[] newArray = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[size++] = data;
    }

    public T pop() {
        if (isEmpty()) throw new NoSuchElementException();
        T tmp = peek();
        array[--size] = null;
        return tmp;
    }

    public T peek() { 
        if (isEmpty()) return null;
        return array[size - 1]; 
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    public Object[] getBackingArray() { return array; }

}
