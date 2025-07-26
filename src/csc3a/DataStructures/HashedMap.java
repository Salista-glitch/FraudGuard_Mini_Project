package csc3a.DataStructures;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;



public class HashedMap<K, V> implements Map<K, V> {
    

	//Attributes
    private final int DEFAULT_CAPACITY = 30;
    private List<Entry<K, V>>[] buckets;
    private int size = 0;

    
    /**
     * Constructor for the map
     */
    @SuppressWarnings("unchecked")
    public HashedMap() {
        buckets = new List[DEFAULT_CAPACITY];
        
        //Set the array of linked lists
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    
    /**
     * Function to get the index that holds a specific key
     * @param key
     * @return
     */
    private int getBucketIndex(K key) {
        return (key == null ? 0 : Math.abs(key.hashCode())) % buckets.length;
    }

    
    /**
     * To get the size of the map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * To check if the map has elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    
    /**
     * To the get a a value based on the key
     */
    @Override
    public V get(K key) {
    	//Get the key
        int index = getBucketIndex(key);
        
        //Iterate through the list
        for (Entry<K, V> entry : buckets[index]) {
            if ((key == null && entry.getKey() == null) || (key != null && key.equals(entry.getKey()))) {
                return entry.getValue();
            }
        }
        //If no value was found
        return null;
    }
    
    /**
     * Function to add a new entry in the map 
     */

    @Override
    public V put(K key, V value) {
        int index = getBucketIndex(key);
      //Iterate through the list
        for (Entry<K, V> entry : buckets[index]) {
            if ((key == null && entry.getKey()== null) || (key != null && key.equals(entry.getKey()))) {
                
            	//Set the the and return old value
            	V oldValue = entry.setValue(value);
                return oldValue;
            }
        }
        
        //create new entry if entry was not found
        buckets[index].add(new Entry<>(key, value));
        size++;
        return null;
    }

    /**
     * Function to remove a value and return the value removed 
     */

    @Override
    public V remove(K key) {
    	
    	
        int index = getBucketIndex(key);
        var iterator = buckets[index].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if ((key == null && entry.getKey() == null) || (key != null && key.equals(entry.getKey()))) {
                V value = entry.getValue();
                iterator.remove(); //remove the entry
                size--;
                return value;
            }
        }
        return null;
    }

    
    /**
     * Returns an iterable list of keys
     */
    @Override
    public Iterable<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    /**
     * Returns an iterable list of values
      */
    @Override
    public Iterable<V> values() {
        List<V> values = new ArrayList<>();
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                values.add(entry.getValue());
            }
        }
        return values;
    }
    /**
     * Returns an iterable list of Entries
      */

    @Override
    public Iterable<Entry<K,V>> entrySet() {
        List<Entry<K, V>> entries = new ArrayList<>();
        for (List<Entry<K, V>> bucket : buckets) {
            entries.addAll(bucket);
        }
        return entries;
    }
}
