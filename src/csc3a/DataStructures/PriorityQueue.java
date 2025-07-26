package csc3a.DataStructures;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A generic priority queue implementation based on a binary heap.
 * Elements are ordered using a user-provided comparator.
 *
 * @param <T> the type of elements held in this queue
 */
public class PriorityQueue<T> {
    private ArrayList<T> heap;
    private Comparator<? super T> comparator;

    /**
     * Constructs a new PriorityQueue with the specified comparator.
     *
     * @param comparator the comparator to determine the ordering of elements
     */
    public PriorityQueue(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
    }

    /**
     * Adds an element to the priority queue.
     *
     * @param element the element to add
     */
    public void add(T element) {
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    /**
     * Retrieves and removes the highest priority element (smallest),
     * or returns {@code null} if the queue is empty.
     *
     * @return the head of the queue, or {@code null} if empty
     */
    public T poll() {
        if (heap.isEmpty()) return null;

        T result = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return result;
    }

    /**
     * Retrieves, but does not remove, the highest priority element (smallest),
     * or returns {@code null} if the queue is empty.
     *
     * @return the head of the queue, or {@code null} if empty
     */
    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    /**
     * Checks whether the priority queue is empty.
     *
     * @return {@code true} if the queue contains no elements, otherwise {@code false}
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements currently in the priority queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return heap.size();
    }

    /**
     * Restores the heap property by moving the element at the given index upward.
     *
     * @param index the index of the element to move up
     */
    private void heapifyUp(int index) {
        T current = heap.get(index);
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            T parent = heap.get(parentIdx);
            if (comparator.compare(current, parent) >= 0) break;

            heap.set(index, parent);
            index = parentIdx;
        }
        heap.set(index, current);
    }

    /**
     * Restores the heap property by moving the element at the given index downward.
     *
     * @param index the index of the element to move down
     */
    private void heapifyDown(int index) {
        int size = heap.size();
        T current = heap.get(index);

        while (index < size / 2) {
            int leftIdx = 2 * index + 1;
            int rightIdx = leftIdx + 1;

            int smallest = leftIdx;
            if (rightIdx < size && comparator.compare(heap.get(rightIdx), heap.get(leftIdx)) < 0) {
                smallest = rightIdx;
            }

            if (comparator.compare(current, heap.get(smallest)) <= 0) break;

            heap.set(index, heap.get(smallest));
            index = smallest;
        }

        heap.set(index, current);
    }
}
