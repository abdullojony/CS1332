import java.util.NoSuchElementException;

public class LinkedStack<T> {
    private LinkedNode<T> head;
    private int size = 0;

    public T pop() {
        if(isEmpty()) throw new NoSuchElementException();
        T tmp = peek();
        head = head.getNext();
        size--;
        return tmp;
    }

    public void push (T data) {
        if (data == null) throw new IllegalArgumentException();
        head = new LinkedNode<T>(data, head);
        size++;
    }

    public T peek() {
        if(isEmpty()) return null;
        return head.getData();
    }

    public int size() {
        return size;
    }

    public LinkedNode<T> getHead() {
        return head;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // public void clear() {
    //     head = null;
    // }
}