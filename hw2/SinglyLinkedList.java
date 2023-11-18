public class SinglyLinkedList<T> {
    private LinkedListNode<T> head;
    int size = 0;

    public boolean isEmpty() { return size == 0; }

    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (data == null) throw new IllegalArgumentException();

        if (index == 0) {
            addToFront(data);
            return;
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(data);
        LinkedListNode<T> curr = head;

        for (int i = 0; i < index - 1; i++) {
            curr = curr.getNext();
        }

        newNode.setNext(curr.getNext());
        curr.setNext(newNode);
        size++;
    }

    public void addToFront(T data) {
        if (data == null) throw new IllegalArgumentException();

        size++;

        if (head == null) {
            head = new LinkedListNode<>(data);
            head.setNext(head);
            return;
        }
        
        LinkedListNode<T> newNode = new LinkedListNode<>(null);
        newNode.setNext(head.getNext());
        head.setNext(newNode);
        newNode.setData(head.getData());
        head.setData(data);
    }

    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (isEmpty()) return null;
        if (index == 0) return removeFromFront();

        LinkedListNode<T> curr = head;

        for (int i = 0; i < index - 1; i++) {
            curr = curr.getNext();
        }
        
        T tmp = curr.getNext().getData();
        curr.setNext(curr.getNext().getNext());
        size--;

        return tmp;
    }

    public T removeFromFront() {
        if (isEmpty()) return null;
        
        T tmp = head.getData();
        head.setData(head.getNext().getData());
        head.setNext(head.getNext().getNext());

        if (--size == 0) head = null;

        return tmp;
    }

    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    public T removeLastOccurrence(T data) {
        if (data == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        LinkedListNode<T> curr = head;
        int i = 0;

        while(!curr.getData().equals(data)){
            curr = curr.getNext();
            if (curr == head) return null;
            i++;
        }
        
        return removeAtIndex(i);
    }

    public T get(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (isEmpty()) return null;

        LinkedListNode<T> curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    public int size() { return size; }

    public LinkedListNode<T> getHead() { return head; }

    public void clear() {
        head = null;
        size = 0;
    }

    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        LinkedListNode<T> curr = head;
        for(int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }
}
