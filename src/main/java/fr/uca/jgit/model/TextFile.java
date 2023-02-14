package fr.uca.jgit.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class TextFile implements Node {
    private String content;

    @Override
    public String hash() {
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /** Stores the corresponding object in .git directory (to file .git/object/[hash]). **/
    @Override
    public void store() {
        try {
            String hash = hash();
            File file = new File(".jgit/objects/" + hash);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Loads the text file corresponding to the given hash (from file .jgit/object/[hash]). **/
    public static TextFile loadFile(String hash) {
        try {
            FileInputStream fis = new FileInputStream(".jgit/object/" + hash);
            ObjectInputStream ois = new ObjectInputStream(fis);
            TextFile textFile = (TextFile) ois.readObject();
            ois.close();
            fis.close();
            return textFile;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /** Restores the file node at the given path. **/
    @Override
    public void restore(String path) {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
