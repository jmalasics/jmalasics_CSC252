package Carver;

import edu.neumont.ui.Picture;
import org.junit.Test;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SeamCarverTest {

    @org.junit.Test
    public void testEnergy() throws Exception {

    }

    @org.junit.Test
    public void testFindHorizontalSeam() throws Exception {

    }

    @org.junit.Test
    public void testFindVerticalSeam() throws Exception {

    }

    @Test
    public void testSecretMessage() throws Exception {
        SeamCarver seamCarver = new SeamCarver(new Picture("C:\\Users\\jmalasics\\IdeaProjects\\MalasicsJ_Carved\\src\\overlayimagewithhiddenmessage.png"));
        for(int i = 0; i < 150; i++) {
            seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        }
        for(int i = 0; i < 150; i++) {
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        }
        Picture picture = seamCarver.getPicture();
        picture.save("carved.png");
    }

    @org.junit.Test
    public void testRemoveHorizontalSeam() throws Exception {
        SeamCarver seamCarver = new SeamCarver(new Picture("C:\\Users\\jmalasics\\IdeaProjects\\MalasicsJ_Carved\\src\\overlayimagewithhiddenmessage.png"));
        for(int i = 0; i < 20; i++) {
            seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        }
        Picture picture = seamCarver.getPicture();
        picture.save("carved.png");
    }

    @org.junit.Test
    public void testRemoveVerticalSeam() throws Exception {
        SeamCarver seamCarver = new SeamCarver(new Picture("C:\\Users\\jmalasics\\IdeaProjects\\MalasicsJ_Carved\\src\\overlayimagewithhiddenmessage.png"));
        for(int i = 0; i < 200; i++) {
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        }
        Picture picture = seamCarver.getPicture();
        picture.save("carved.png");
        /*
        JFrame window = new JFrame("Carved Picture");
        window.setBounds(100, 100, picture.width() + 10, picture.height() + 10);
        window.setVisible(true);
        window.add(picture.getJLabel());
        window.validate();
        */
    }
}