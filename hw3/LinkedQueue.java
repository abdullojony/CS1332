import java.util.NoSuchElementException;

public class LinkedQueue<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size = 0;

    public void enqueue(T data) {
        if (data == null) throw new IllegalArgumentException();
        // addToBack
        if (isEmpty()) {
            tail = new LinkedNode<>(data);
            head = tail;
        } else {
            tail.setNext(new LinkedNode<>(data));
            tail = tail.getNext();
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        T tmp = head.getData();
        head = head.getNext();
        if (--size == 0) tail = null;
        return tmp;
    }

    public T peek() {
        if (isEmpty()) return null;
        return head.getData();
    }

    public LinkedNode<T> getHead() { return head; }

    public LinkedNode<T> getTail() { return tail; }

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }
}
