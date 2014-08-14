package HuffmanTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import PriorityQueue.QueueNode;
import edu.neumont.io.Bits;

/**
 * Created by jmalasics on 8/8/2014.
 */
public class HuffmanTree<T> {

    public static void main(String[] args) {
        String msg = "Hello World";
        byte[] originalBytes = msg.getBytes();
        Byte[] bytes = new Byte[originalBytes.length];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = new Byte(originalBytes[i]);
        }
        HuffmanTree<Byte> tree = new HuffmanTree<Byte>(bytes);
        Bits bits = new Bits();
        bits.push(false);
        bits.push(true);
        bits.push(true);
        System.out.println("To byte: " + tree.toByte(bits));
        Bits bitsTwo = new Bits();
        byte myByte = (byte) 100;
        tree.fromByte(myByte, bitsTwo);
        tree.inOrder();
    }

    private Node<T> root;
    private HashMap<T, Integer> frequencyTable = new HashMap<T, Integer>();
    private PriorityQueue<QueueNode<T>> queue = new PriorityQueue<QueueNode<T>>();
    private ArrayList<Node<T>> disconnectedNodes = new ArrayList<Node<T>>();

    public HuffmanTree(T[] values) {
        buildFrequencyTable(values);
        printFrequencyTable();
        buildQueue();
        fillTree();
    }

    private void fillTree() {
        while(queue.size() > 1) {
            QueueNode<T> one = queue.poll();
            QueueNode<T> two = queue.poll();
            buildTree(one, two);
        }
    }

    private void buildTree(QueueNode<T> one, QueueNode<T> two) {
        int totalFrequency = one.getFrequency() + two.getFrequency();
        if(root == null) {
            ArrayList<T> values = new ArrayList<T>();
            values.addAll(one.getValues());
            values.addAll(two.getValues());
            root = new Node<T>(values);
            QueueNode<T> node = new QueueNode<T>(values, totalFrequency);
            queue.add(node);
            root.setLeft(new Node<T>(one.getValues()));
            root.setRight(new Node<T>(two.getValues()));
        } else {
            if(treeContains(root, one.getValues())) {
                Node<T> temp = root;
                ArrayList<T> newValues = new ArrayList<T>();
                newValues.addAll(temp.getValues());
                newValues.addAll(two.getValues());
                root = new Node<T>(newValues);
                root.setLeft(temp);
                root.setRight(disconnectedNodes.get(disconnectedNodes.indexOf(new Node<T>(two.getValues()))));
                queue.add(new QueueNode<T>(newValues, totalFrequency));
            } else if(treeContains(root, two.getValues())) {
                Node<T> temp = root;
                ArrayList<T> newValues = new ArrayList<T>();
                newValues.addAll(one.getValues());
                newValues.addAll(temp.getValues());
                root = new Node<T>(newValues);
                root.setLeft(disconnectedNodes.get(disconnectedNodes.indexOf(new Node<T>(one.getValues()))));
                root.setRight(temp);
                queue.add(new QueueNode<T>(newValues, totalFrequency));
            } else {
                if(disconnectedNodes.size() > 0) {
                    for(int i = 0; i < disconnectedNodes.size(); i++) {
                        Node<T> node = disconnectedNodes.get(i);
                        if (node.containsAll(one.getValues())) {
                            disconnectedNodes.remove(node);
                            buildOffRight(node, two, totalFrequency);
                            i = disconnectedNodes.size() + 1;
                        } else if (node.containsAll(two.getValues())) {
                            disconnectedNodes.remove(node);
                            buildOffLeft(one, node, totalFrequency);
                            i = disconnectedNodes.size() + 1;
                        } else {
                            buildDisconnectedTree(one, two, totalFrequency);
                            i = disconnectedNodes.size() + 1;
                        }
                    }
                } else {
                    buildDisconnectedTree(one, two, totalFrequency);
                }
            }
        }
    }

    private void buildDisconnectedTree(QueueNode<T> one, QueueNode<T> two, int totalFrequency) {
        ArrayList<T> newValues = new ArrayList<T>();
        newValues.addAll(one.getValues());
        newValues.addAll(two.getValues());
        Node<T> node = new Node<T>(newValues);
        node.setLeft(new Node<T>(one.getValues()));
        if(disconnectedNodes.contains(new Node<T>(two.getValues()))) {
            node.setRight(disconnectedNodes.get(disconnectedNodes.indexOf(new Node<T>(two.getValues()))));
        } else {
            node.setRight(new Node<T>(two.getValues()));
        }
        queue.add(new QueueNode<T>(newValues, totalFrequency));
        disconnectedNodes.add(node);
    }

    private void buildOffLeft(QueueNode<T> one, Node<T> temp, int totalFrequency) {
        Node<T> node;
        ArrayList<T> newValues = new ArrayList<T>();
        newValues.addAll(one.getValues());
        newValues.addAll(temp.getValues());
        node = new Node<T>(newValues);
        node.setLeft(new Node<T>(one.getValues()));
        node.setRight(temp);
        queue.add(new QueueNode<T>(newValues, totalFrequency));
        disconnectedNodes.add(node);
    }

    private void buildOffRight(Node<T> temp, QueueNode<T> two, int totalFrequency) {
        Node<T> node;
        ArrayList<T> newValues = new ArrayList<T>();
        newValues.addAll(temp.getValues());
        newValues.addAll(two.getValues());
        node = new Node<T>(newValues);
        node.setLeft(temp);
        node.setRight(new Node<T>(two.getValues()));
        queue.add(new QueueNode<T>(newValues, totalFrequency));
        disconnectedNodes.add(node);
    }

    private void buildFrequencyTable(T[] values) {
        for(int i = 0; i < values.length; i++) {
            T value = values[i];
            if(frequencyTable.containsKey(value)) {
                int frequency = frequencyTable.get(value);
                frequencyTable.remove(value);
                frequencyTable.put(value, ++frequency);
            } else if(!frequencyTable.containsKey(value)) {
                frequencyTable.put(value, 1);
            }
        }
    }

    private void buildQueue() {
        Set<T> keySet = frequencyTable.keySet();
        for(T value : keySet) {
            ArrayList<T> values = new ArrayList<T>();
            values.add(value);
            queue.add(new QueueNode<T>(values, frequencyTable.get(value)));
        }
    }

    public T toByte(Bits bits) {
        return toByte(bits, root);
    }

    private T toByte(Bits bits, Node<T> current) {
        if(current.getRight() == null) {
            return current.getValues().get(0);
        }
        boolean isRight = bits.pollFirst();
        if(isRight) {
            return toByte(bits, current.getRight());
        } else {
            return toByte(bits, current.getLeft());
        }
    }

    public void fromByte(T t, Bits bits) {
        fromByte(t, bits, root);
    }

    private void fromByte(T t, Bits bits, Node<T> current) {
        if(current.getLeft() != null) {
            if (current.getLeft().contains(t)) {
                bits.push(false);
                if(current.getLeft().getValues().size() > 1) {
                    fromByte(t, bits, current.getLeft());
                }
            } else if (current.getRight().contains(t)) {
                bits.push(true);
                if(current.getRight().getValues().size() > 1) {
                    fromByte(t, bits, current.getRight());
                }
            }
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println("");
    }

    private void inOrder(Node<T> current) {
        if(current != null) {
            inOrder(current.getLeft());
            visit(current);
            inOrder(current.getRight());
        }
    }

    private void visit(Node<T> node) {
        node.printValues();
    }

    private boolean treeContains(Node<T> node, ArrayList<T> values) {
        return node.containsAll(values);
    }

    public void printDisconnected() {
        System.out.println("Disconnected: ");
        for(Node<T> node: disconnectedNodes) {
            node.printValues();
        }
    }

    public void printFrequencyTable() {
        System.out.print("Table: ");
        for(T value: frequencyTable.keySet()) {
            System.out.print(value + " " + frequencyTable.get(value) + ", ");
        }
        System.out.println("");
    }

}