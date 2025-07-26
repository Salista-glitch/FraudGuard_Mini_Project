package csc3a.DataStructures;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class HashedSet<E> implements Iterable<E> {
	
	//Attributes
    private final int DEFAULT_CAPACITY = 16;
    private List<E>[] buckets;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashedSet() {
        buckets = new List[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int getBucketIndex(E element) {
        return (element == null ? 0 : Math.abs(element.hashCode())) % buckets.length;
    }

    public boolean add(E element) {
        int index = getBucketIndex(element);
        List<E> bucket = buckets[index];

        if (!bucket.contains(element)) {
            bucket.add(element);
            size++;
            return true;
        }
        return false; // already present
    }

    public boolean remove(E element) {
        int index = getBucketIndex(element);
        List<E> bucket = buckets[index];

        if (bucket.remove(element)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(E element) {
        int index = getBucketIndex(element);
        return buckets[index].contains(element);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (List<E> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        List<E> all = new ArrayList<>();
        for (List<E> bucket : buckets) {
            all.addAll(bucket);
        }
        return all.iterator();
    }
}
