package hw1;
public class ArrayList<T> {
    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int size;

    public ArrayList(){
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() { return size; }

    public T[] getBackingArray() { return backingArray; }

    public Boolean isEmpty() { return size == 0; }

    public void clear() {
        backingArray = (T[]) new Object[backingArray.length];
        size = 0;
    }

    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        if (data == null) throw new IllegalArgumentException();
        
        if (size + 1 > backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++){
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        
        if (backingArray[index] != null) {
            for (int j = size; j > index; j--) {
                backingArray[j] = backingArray[j-1];
            }
        }

        backingArray[index] = data;

        size++;
    }

    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    public T removeAtIndex(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();

        T e = backingArray[index];

        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }

        backingArray[--size] = null;
        
        return e;
    }

    public T removeFromFront() {
        return removeAtIndex(0);
    }

    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        return backingArray[index];
    }

    public int lastIndexOf(T data) {
        if (data == null) throw new IllegalArgumentException();

        for (int i = size - 1; i >= 0; i--) {
            if (data.equals(backingArray[i])) return i;
        }

        return -1;
    }
    
}