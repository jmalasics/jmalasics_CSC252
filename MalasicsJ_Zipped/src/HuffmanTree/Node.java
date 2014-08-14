package HuffmanTree;

import java.util.ArrayList;

/**
 * Created by jmalasics on 8/8/2014.
 */
public class Node<T> {

    private ArrayList<T> values = new ArrayList<T>();
    private Node<T> left;
    private Node<T> right;

    public Node(ArrayList<T> values) {
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

    public boolean containsAll(ArrayList<T> values) {
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

    public boolean hasOneValue() {
        return values.size() == 1;
    }

    public void printValues() {
        for(T value : values) {
            System.out.print(value + ",");
        }
        System.out.print("  ");
    }

    public ArrayList<T> getValues() {
        return values;
    }

}
