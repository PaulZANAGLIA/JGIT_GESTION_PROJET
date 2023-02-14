package fr.uca.jgit.model;

import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


public class Folder implements Node, Serializable {
    // Mapping Name -> Node
    private Map<String, Node> children;

    public Folder() {
        children = new HashMap<>();
    }

    @Override
    public String hash() {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (Node child : children.values()) {
                md.update(child.hash().getBytes());
            }
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /** Stores the corresponding object in .git directory (file .jgit/object/[hash]) **/
    @Override
    public void store() {
        try {
            String hash = hash();
            FileOutputStream fos = new FileOutputStream(".jgit/objects/" + hash);
            System.out.println(".jgit/objects/" + hash + " created");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Return a list representation of the folder (see doc) **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Node> entry : children.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\n");
        }
        return sb.toString();
    }

    /** Loads the folder corresponding to the given hash (from file .git/object/[hash]). **/
    public static Folder loadFolder(String hash) {
        try {
            FileInputStream fis = new FileInputStream(".jgit/objects/" + hash);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Folder folder = (Folder) ois.readObject();
            ois.close();
            fis.close();
            return folder;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /** Restores the file node at the given path. **/
    @Override
    public void restore(String path) {
        File folder = new File(path);
        folder.mkdirs();
        for (Map.Entry<String, Node> entry : children.entrySet()) {
            String childPath = path + "/" + entry.getKey();
            entry.getValue().restore(childPath);
        }
    }

    public void addChild(String name, Node child) {
        children.put(name, child);
    }

    public Map<String, Node> getChildren() {
        return children;
    }
}

