package PriorityQueue;


/**
 * Created by jmalasics on 8/19/2014.
 */
public class HeapBasedPriorityQueue<T extends Comparable> implements PriorityQueue<T>{

    private T[] heap;
    private int numValues = 0;

    public HeapBasedPriorityQueue() {
        heap = (T[]) new Comparable[10];
    }

    @Override
    public boolean offer(T data) {
        if(heap.length == numValues) {
            extendArray();
        }
        heap[numValues] = data;
        numValues++;
        heapify();
        return true;
    }

    @Override
    public T peek() {
        T value = null;
        if(heap.length > 0) {
            value = heap[0];
        }
        return value;
    }

    @Override
    public T poll() {
        T polled = heap[0];
        heap[0] = heap[numValues - 1];
        heap[numValues - 1] = null;
        numValues--;
        heapify();
        return polled;
    }

    @Override
    public boolean hasNext() {
        return numValues > 0;
    }

    private void heapify() {
        for(int i = numValues / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if(left < numValues && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }
        if(right < numValues && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }
        if(largest != i) {
            T temp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = temp;
            heapify(largest);
        }
    }

    private void extendArray() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];
        for(int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    @Override
    public int size() {
        return numValues;
    }

}
