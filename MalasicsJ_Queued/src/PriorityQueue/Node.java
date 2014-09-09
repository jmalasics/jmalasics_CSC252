package PriorityQueue;

/**
 * Created by jmalasics on 8/19/2014.
 */
public class Node<T> {

    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node(T value) {
        this.value = value;
    }

    public void setLeft(Node<T> node) {
        left = node;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setRight(Node<T> node) {
        right = node;
    }

    public Node<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

}
