package PriorityQueue;

/**
 * Created by jmalasics on 8/20/2014.
 */
public class Program {

    public static void main(String[] args) {
        PriorityQueue<Integer> heapQueue = new HeapBasedPriorityQueue<Integer>();
        AVLBasedPriorityQueue<Integer> avlQueue = new AVLBasedPriorityQueue<Integer>();
        heapQueue.offer(10);
        heapQueue.offer(3);
        heapQueue.offer(6);
        heapQueue.offer(5);
        heapQueue.offer(1);
        heapQueue.offer(2);
        heapQueue.offer(4);
        heapQueue.offer(9);
        heapQueue.offer(7);
        heapQueue.offer(8);
        avlQueue.offer(10);
        avlQueue.offer(3);
        avlQueue.offer(6);
        avlQueue.offer(5);
        avlQueue.offer(1);
        avlQueue.offer(2);
        avlQueue.offer(4);
        avlQueue.offer(9);
        avlQueue.offer(7);
        avlQueue.offer(8);
        avlQueue.preOrder();
        System.out.println("Priority Queue (Heap): ");
        while(heapQueue.hasNext()) {
            System.out.println("Peek: " + heapQueue.peek());
            System.out.println("Poll: " + heapQueue.poll());
        }
        System.out.println("Priority Queue (AVL): ");
        while(avlQueue.hasNext()) {
            System.out.println("Peek: " + avlQueue.peek());
            System.out.println("Poll: " + avlQueue.poll());
        }
    }

}
