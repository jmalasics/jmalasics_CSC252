package HuffmanTree;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jmalasics on 8/8/2014.
 */
public class Node<T> {

    private Set<T> values = new HashSet<T>();
    private Node<T> left;
    private Node<T> right;

    public Node(Set<T> values) {
        this.values = values;
    }

    public boolean contains(T t) {
        return values.contains(t);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node) {
            Node<T> otherNode = (Node<T>) obj;
            return this.containsAll(otherNode.values) && otherNode.containsAll(this.values);
        }
        return false;
    }

    public boolean containsAll(Set<T> values) {
        return this.values.containsAll(values);
    }

    public void setLeft(Node<T> node) {
        left = node;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node<T> node) {
        right = node;
    }

    public Node getRight() {
        return right;
    }

    public void printValues() {
        for(T value : values) {
            System.out.print(value + ",");
        }
        System.out.print("  ");
    }

    public Set<T> getValues() {
        return values;
    }

}
