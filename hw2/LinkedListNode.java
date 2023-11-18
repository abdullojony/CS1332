public class LinkedListNode<T> {
    private LinkedListNode<T> next;
    private T data;

    public LinkedListNode(T data, LinkedListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public LinkedListNode(T data) {
        this.data = data;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LinkedListNode<T> getNext() { return next; }
    public void setNext(LinkedListNode<T> next) { this.next = next; }

    public String toString() {
        return "Node containing: " + data;
    }
}
