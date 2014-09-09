package PriorityQueue;

/**
 * Created by jmalasics on 8/19/2014.
 */
public class AVLBasedPriorityQueue<T extends Comparable> implements PriorityQueue<T>{

    private Node<T> root;
    private int numValues = 0;

    @Override
    public boolean offer(T data) {
        boolean offered = false;
        if(root == null) {
            root = new Node<T>(data);
            offered = true;
        } else if(data.compareTo(root.getValue()) == -1) {
            if(root.getLeft() == null) {
                root.setLeft(new Node<T>(data));
                offered = true;
            } else {
                offered = offer(data, root.getLeft(), root);
            }
        } else if(data.compareTo(root.getValue()) == 1) {
            if(root.getRight() == null) {
                root.setRight(new Node<T>(data));
                offered = true;
            } else {
                offered = offer(data, root.getRight(), root);
            }
        }
        balance(root, null);
        if(offered) {
            numValues++;
        }
        return offered;
    }

    private boolean offer(T data, Node<T> current, Node<T> parent) {
        boolean offered = false;
        if(data.compareTo(current.getValue()) == -1) {
            if(current.getLeft() == null) {
                current.setLeft(new Node<T>(data));
                offered = true;
            } else {
                offered = offer(data, current.getLeft(), current);
            }
        } else if(data.compareTo(current.getValue()) == 1) {
            if(current.getRight() == null) {
                current.setRight(new Node<T>(data));
                offered = true;
            } else {
                offered = offer(data, current.getRight(), current);
            }
        }
        balance(current, parent);
        return offered;
    }

    @Override
    public T peek() {
        T value = null;
        if(root != null) {
            if(root.getRight() != null) {
                value = getRightMostParent(root, null).getRight().getValue();
            } else {
                value = root.getValue();
            }
        }
        return value;
    }

    @Override
    public T poll() {
        Node<T> max = null;
        if(root != null) {
            if (root.getRight() != null) {
                Node<T> maxParent = getRightMostParent(root, null);
                max = maxParent.getRight();
                maxParent.setRight(max.getLeft());
            } else {
                max = root;
                root = root.getLeft();
            }
            numValues--;
        }
        return max.getValue();
    }

    @Override
    public boolean hasNext() {
        return root != null;
    }

    private Node<T> getRightMostParent(Node<T> current, Node<T> parent) {
        Node<T> node = null;
        if(current != null) {
            if(current.getRight() == null) {
                node = parent;
            } else {
                node = getRightMostParent(current.getRight(), current);
            }
        }
        return node;
    }

    private void leftRotation(Node<T> current, Node<T> parent) {
        Node<T> temp = current.getRight();
        current.setRight(temp.getLeft());
        temp.setLeft(current);
        if(parent != null) {
            if(parent.getValue().compareTo(temp.getValue()) > 0) {
                parent.setLeft(temp);
            } else {
                parent.setRight(temp);
            }
        } else {
            root = temp;
        }
    }

    private void rightRotation(Node<T> current, Node<T> parent) {
        Node<T> temp = current.getLeft();
        current.setLeft(temp.getRight());
        temp.setRight(current);
        if(parent != null) {
            if(parent.getValue().compareTo(temp.getValue()) > 0) {
                parent.setLeft(temp);
            } else {
                parent.setRight(temp);
            }
        } else {
            root = temp;
        }
    }

    private void leftRightRotation(Node<T> node, Node<T> parent) {
        leftRotation(node.getLeft(), node);
        rightRotation(node, parent);
    }

    private void rightLeftRotation(Node<T> node, Node<T> parent) {
        rightRotation(node.getRight(), node);
        leftRotation(node, parent);
    }

    public void preOrder() {
        preOrder(root);
        System.out.println("");
    }

    private void preOrder(Node<T> current) {
        if(current != null) {
            visit(current);
            preOrder(current.getLeft());
            preOrder(current.getRight());
        }
    }

    private void visit(Node<T> node) {
        System.out.print(node.getValue() + ", ");
    }

    public int height(Node<T> node) {
        if(node == null) {
            return 0;
        }
        return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
    }

    private int balanceFactor(Node<T> node) {
        return height(node.getLeft()) - height(node.getRight());
    }

    private void balance(Node<T> node, Node<T> parent) {
        int balanceFactor = balanceFactor(node);
        if(balanceFactor > 1) {
            if(balanceFactor(node.getLeft()) >= 0) {
                rightRotation(node, parent);
            } else {
                leftRightRotation(node, parent);
            }
        }
        if(balanceFactor < -1) {
            if(balanceFactor(node.getRight()) <= 0) {
                leftRotation(node, parent);
            } else {
                rightLeftRotation(node, parent);
            }
        }
    }

    @Override
    public int size() {
        return numValues;
    }

}
