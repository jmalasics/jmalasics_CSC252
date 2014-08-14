package PriorityQueue;

import java.util.ArrayList;

/**
 * Created by jmalasics on 8/11/2014.
 */
public class QueueNode<T> implements Comparable {

    private ArrayList<T> values;
    private int frequency;

    public QueueNode(ArrayList<T> values, int frequency) {
        this.values = values;
        this.frequency = frequency;
    }

    public ArrayList<T> getValues() {
        return values;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            QueueNode otherNode = (QueueNode) obj;
            return otherNode.values.containsAll(this.values) && this.values.containsAll(otherNode.values);
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        int otherFrequencty = ((QueueNode) o).frequency;
        if(frequency > otherFrequencty) {
            return 1;
        } else if(frequency < otherFrequencty) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Value: " + valuesString() + " Frequency: " + frequency;
    }

    private String valuesString() {
        StringBuilder builder = new StringBuilder();
        for(T value : values) {
            builder.append(value + ",");
        }
        return builder.toString();
    }
}
