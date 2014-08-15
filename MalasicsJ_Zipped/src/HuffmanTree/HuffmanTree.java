package HuffmanTree;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import PriorityQueue.QueueNode;
import edu.neumont.io.Bits;

/**
 * Created by jmalasics on 8/8/2014.
 */
public class HuffmanTree<T> {

    public static void main(String[] args) {
        try {
            String msg = "Hello World";
            byte[] picCompressedBytes = Files.readAllBytes(Paths.get("C:\\Users\\jmalasics\\Documents\\GitHub\\jmalasics_CSC252\\MalasicsJ_Zipped\\src", "compressed.huff"));
            byte[] originalBytes = new byte[] {-1, -50, -70, 120, 127}; //Files.readAllBytes(Paths.get("C:\\Users\\jmalasics\\Pictures", "ph.jpg"));
            /*byte[] originalBytes = new byte[54679];
            int[] frequencies = new int[] {423, 116, 145, 136, 130, 165, 179, 197, 148, 125, 954, 156, 143, 145, 164, 241, 107, 149, 176, 153,
                    121, 164, 144, 166, 100, 138, 157, 140, 119, 138, 178, 289, 360, 120, 961, 195, 139, 147, 129, 192, 119, 146, 138, 184, 137,
                    196, 163, 331, 115, 160, 127, 172, 176, 181, 149, 194, 138, 154, 163, 167, 196, 174, 250, 354, 142, 169, 170, 209, 205, 179,
                    147, 245, 108, 179, 148, 186, 131, 160, 112, 219, 118, 204, 164, 154, 154, 175, 189, 239, 126, 145, 185, 179, 149, 167, 152,
                    244, 189, 257, 234, 208, 179, 170, 171, 178, 184, 189, 203, 184, 204, 208, 187, 163, 335, 326, 206, 189, 210, 204, 230, 202,
                    415, 240, 275, 295, 375, 308, 401, 608, 2099, 495, 374, 160, 130, 331, 107, 181, 117, 133, 476, 129, 137, 106, 107, 237, 184,
                    143, 122, 143, 1596, 205, 121, 170, 123, 124, 150, 132, 143, 133, 178, 308, 96, 102, 114, 176, 159, 149, 123, 199, 1156, 119,
                    144, 237, 131, 155, 143, 225, 92, 125, 117, 138, 135, 154, 124, 137, 121, 143, 149, 141, 177, 159, 247, 384, 302, 120, 95, 140,
                    87, 1460, 155, 199, 111, 198, 147, 182, 91, 148, 119, 233, 445, 1288, 138, 133, 122, 170, 156, 257, 143, 149, 180, 174, 132, 151,
                    193, 347, 91, 119, 135, 182, 124, 152, 109, 175, 152, 159, 166, 224, 126, 169, 145, 220, 119, 148, 133, 158, 144, 185, 139, 168, 244,
                    145, 167, 167, 262, 214, 293, 402};
            byte b = (byte) -128;
            int currentIndex = 0;
            for(int i = 0; i < frequencies.length; i++) {
                int loopCount = frequencies[i];
                int count = 0;
                while(count < loopCount) {
                    originalBytes[currentIndex] = b;
                    currentIndex++;
                    count++;
                }
                b++;
            }*/
            Byte[] bytes = new Byte[originalBytes.length];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = new Byte(originalBytes[i]);
            }
            HuffmanTree<Byte> tree = new HuffmanTree<Byte>(bytes);
            HuffmanCompressor compressor = new HuffmanCompressor();
            System.out.println("Starting bytes size: " + bytes.length);
            byte[] compressedBytes = compressor.compress(tree, originalBytes);
            System.out.println("Compressed bytes size: " + compressedBytes.length);
            byte[] uncompressedBytes = compressor.decompress(tree, bytes.length, compressedBytes);
            System.out.println("Uncompressed bytes size: " + uncompressedBytes.length);
            Files.write(Paths.get("C:\\Users\\jmalasics\\Documents\\GitHub\\jmalasics_CSC252\\MalasicsJ_Zipped\\src", "uncompressed.jpg"), uncompressedBytes);
        } catch(Exception e) {
            e.printStackTrace();
        }
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
            Set<T> values = new HashSet<T>();
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
                Set<T> newValues = new HashSet<T>();
                newValues.addAll(temp.getValues());
                newValues.addAll(two.getValues());
                root = new Node<T>(newValues);
                root.setLeft(temp);
                Iterator setIterator = disconnectedNodes.iterator();
                while(setIterator.hasNext()) {
                    Node<T> node = (Node<T>) setIterator.next();
                    if(node.containsAll(two.getValues())) {
                        root.setRight(node);
                        disconnectedNodes.remove(node);
                        break;
                    }
                }
                if(root.getRight() == null) {
                    root.setRight(new Node<T>(two.getValues()));
                }
                queue.add(new QueueNode<T>(newValues, totalFrequency));
            } else if(treeContains(root, two.getValues())) {
                Node<T> temp = root;
                Set<T> newValues = new HashSet<T>();
                newValues.addAll(one.getValues());
                newValues.addAll(temp.getValues());
                root = new Node<T>(newValues);
                Iterator setIterator = disconnectedNodes.iterator();
                while(setIterator.hasNext()) {
                    Node<T> node = (Node<T>) setIterator.next();
                    if(node.containsAll(one.getValues())) {
                        root.setLeft(node);
                        disconnectedNodes.remove(node);
                        break;
                    }
                }
                if(root.getLeft() == null) {
                    root.setLeft(new Node<T>(one.getValues()));
                }
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
        Set<T> newValues = new HashSet<T>();
        newValues.addAll(one.getValues());
        newValues.addAll(two.getValues());
        Node<T> node = new Node<T>(newValues);
        node.setLeft(new Node<T>(one.getValues()));
        node.setRight(new Node<T>(two.getValues()));
        queue.add(new QueueNode<T>(newValues, totalFrequency));
        disconnectedNodes.add(node);
    }

    private void buildOffLeft(QueueNode<T> one, Node<T> temp, int totalFrequency) {
        Node<T> node;
        Set<T> newValues = new HashSet<T>();
        newValues.addAll(one.getValues());
        newValues.addAll(temp.getValues());
        node = new Node<T>(newValues);
        if(disconnectedNodes.contains(new Node<T>(one.getValues()))) {
            Iterator setIterator = disconnectedNodes.iterator();
            while (setIterator.hasNext()) {
                Node<T> nodeTwo = (Node<T>) setIterator.next();
                if (nodeTwo.containsAll(one.getValues())) {
                    node.setLeft(nodeTwo);
                    disconnectedNodes.remove(nodeTwo);
                    break;
                }
            }
        } else {
            node.setLeft(new Node<T>(one.getValues()));
        }
        node.setRight(temp);
        queue.add(new QueueNode<T>(newValues, totalFrequency));
        disconnectedNodes.add(node);
    }

    private void buildOffRight(Node<T> temp, QueueNode<T> two, int totalFrequency) {
        Node<T> node;
        Set<T> newValues = new HashSet<T>();
        newValues.addAll(temp.getValues());
        newValues.addAll(two.getValues());
        node = new Node<T>(newValues);
        node.setLeft(temp);
        if(disconnectedNodes.contains(new Node<T>(two.getValues()))) {
            Iterator setIterator = disconnectedNodes.iterator();
            while(setIterator.hasNext()) {
                Node<T> nodeTwo = (Node<T>) setIterator.next();
                if(nodeTwo.containsAll(two.getValues())) {
                    node.setRight(nodeTwo);
                    disconnectedNodes.remove(nodeTwo);
                    break;
                }
            }
        } else {
            node.setRight(new Node<T>(two.getValues()));
        }
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
            Set<T> values = new HashSet<T>();
            values.add(value);
            queue.add(new QueueNode<T>(values, frequencyTable.get(value)));
        }
    }

    public T toByte(Bits bits) {
        return toByte(bits, root);
    }

    private T toByte(Bits bits, Node<T> current) {
        if(current.getRight() == null && current.getLeft() == null) {
            return (T) current.getValues().toArray()[0];
        }
        boolean isRight = bits.poll();
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
        if(current.getLeft() != null && current.getRight() != null) {
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

    private boolean treeContains(Node<T> node, Set<T> values) {
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