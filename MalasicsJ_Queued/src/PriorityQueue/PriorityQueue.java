package PriorityQueue;

/**
 * Created by jmalasics on 8/20/2014.
 */
public interface PriorityQueue<T extends Comparable> {

    public boolean offer(T data);

    public T peek();

    public T poll();

    public boolean hasNext();

    public int size();

}
