package HuffmanTree;

import edu.neumont.io.Bits;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by jmalasics on 8/14/2014.
 */
public class HuffmanCompressor {

    public byte[] compress(HuffmanTree<Byte> tree, byte[] bytes) {
        Bits bits = new Bits();
        for(int i = 0; i < bytes.length; i++) {
            tree.fromByte(bytes[i], bits);
        }
        byte[] compressedBytes = new byte[bits.size() / 8 + 1];
        int currentIndex = 0;
        int count = 0;
        int power = 7;
        int byteValue = 0;
        boolean running = true;
        while(running) {
            if(!bits.isEmpty()) {
                int bitValue = bits.pollLast() ? 1 : 0;
                byteValue += bitValue * Math.pow(2, power);
                count++;
                power--;
                if (count == 8) {
                    compressedBytes[currentIndex] = (byte) byteValue;
                    currentIndex++;
                    power = 7;
                    count = 0;
                    byteValue = 0;
                }
            } else {
                compressedBytes[currentIndex] = (byte) byteValue;
                running = false;
            }
        }
        return compressedBytes;
    }

    public byte[] decompress(HuffmanTree<Byte> tree, int uncompressedLength, byte[] bytes) {
        byte[] uncompressedBytes = new byte[uncompressedLength];
        Bits bits = new Bits();
        for(int i = 0; i < bytes.length; i++) {
            Byte b = bytes[i];
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            for(char c : binaryString.toCharArray()) {
                int bit = c - '0';
                if(bit == 1) {
                    bits.push(true);
                } else {
                    bits.push(false);
                }
            }
        }
        for(int i = 0; i < uncompressedBytes.length; i++) {
            uncompressedBytes[i] = tree.toByte(bits);
        }
        return uncompressedBytes;
    }

}
