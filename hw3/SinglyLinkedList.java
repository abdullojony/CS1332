public class SinglyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        Node(T data) {
            this.data = data;
        }
    }

    public boolean isEmpty() { return size==0; }

    public void addToFront(T data) {
        head = new Node<>(data, head);
        if (isEmpty()) tail = head;
        size++;
    }

    public void addToBack(T data) {
        if (isEmpty()) {
            addToFront(data);
            return;
        }
        tail.next = new Node<>(data);
        tail = tail.next;
        size++;
    }

    public T removeFromFront() {
        if(isEmpty()) return null;
        T tmp = head.data;
        head = head.next;
        size--;
        if(isEmpty()) tail = null;
        return tmp;
    }

    public void clear() {
        head = null;
        tail = null;
    }

}
