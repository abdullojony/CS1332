import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;

/**
 * Your implementation of HashMap.
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 11;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        // Your code here
        this(INITIAL_CAPACITY);
    }

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of the initialCapacity parameter.
     *
     * You may assume the initialCapacity parameter will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        // Your code here
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     *
     * Your code here
     */

    public V put(K key, V value) {
        // Your code here
        if (key == null || value == null) throw new IllegalArgumentException();
        if ((size + 1) / table.length >= MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int index = compress(key);
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        if (table[index] != null) {
            for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.getNext()) {
                if (entry.getKey().equals(key)) {
                    V tmp = entry.getValue();
                    entry.setValue(value);
                    return tmp;
                }
            }
            newEntry.setNext(table[index]);
            table[index] = newEntry;
        }
        table[index] = newEntry;
        size++;
        
        return null;
    }

    /**
     * Resizes the backing table to the specified length.
     *
     * Your code here
     */

    public void resizeBackingTable(int length) {
        // Your code here
        if (length < 0 || length < size) throw new IllegalArgumentException();
        MapEntry<K, V>[] newTable = new MapEntry[length];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                int index = compress(entry.getKey());
                newTable[index] = entry;
            }
        }
        table = newTable;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * Your code here
     */

    public V remove(K key) {
        // Your code here
        if (key == null) throw new IllegalArgumentException();
        int index = compress(key);
        if (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                V tmp = table[index].getValue();
                table[index] = table[index].getNext();
                size--;
                return tmp;
            }
            for (MapEntry<K, V> entry = table[index]; entry.getNext() != null; entry = entry.getNext()) {
                if (entry.getNext().getKey().equals(key)) {
                    V tmp = table[index].getNext().getValue();
                    table[index].setNext(table[index].getNext().getNext());
                    size--;
                    return tmp;
                }
            }
        }
        throw new NoSuchElementException();
    }

    private int compress(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * Gets the value associated with the given key.
     *
     * Your code here
     */

    public V get(K key) {
        // Your code here
        if (key == null) throw new IllegalArgumentException();
        int index = compress(key);
        if (table[index] != null) {
            for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.getNext()) {
                if (entry.getKey().equals(key)) return entry.getValue();
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * Your code here
     */

    public boolean containsKey(K key) {
        // Your code here
        int index = compress(key);
        if (table[index] != null) {
            for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.getNext()) {
                if (entry.getKey().equals(key)) return true;
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Your code here
     */

    public Set<K> keySet() {
        // Your code here
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            for (MapEntry<K, V> entry = table[i]; entry != null; entry = entry.getNext()) {
                keySet.add(entry.getKey());
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Your code here
     */

    public List<V> values() {
        // Your code here
        List<V> valList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            for (MapEntry<K, V> entry = table[i]; entry != null; entry = entry.getNext()) {
                valList.add(entry.getValue());
            }
        }
        return valList;
    }

    /**
     * Clears the table and resets it to a new table of length INITIAL_CAPACITY.
     */

    public void clear() {
        // Your code here
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the HashMap.
     *
     * @return number of items in the HashMap
     */
    public int size() {
        // Your code here
        return size;
    }

    /**
     * Returns the backing table of the HashMap.
     *
     * @return the backing table of the HashMap
     */
    public MapEntry<K, V>[] getTable() {
        // Your code here
        return table;
    }
}
