package fr.uca.jgit.model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.List;

public class Commit implements JGitObject {
    private List<Commit> parents;
    private Folder state;

    @Override
    public String hash() {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] parentHashes = parents.stream().map(Commit::hash).map(String::getBytes).reduce(new byte[0], (a, b) -> {
                byte[] result = new byte[a.length + b.length];
                System.arraycopy(a, 0, result, 0, a.length);
                System.arraycopy(b, 0, result, a.length, b.length);
                return result;
            });
            md.update(parentHashes);
            md.update(state.hash().getBytes());
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void store() {
        try {
            String hash = hash();
            FileOutputStream fos = new FileOutputStream(".git/logs/" + hash);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Commit loadCommit(String hash) {
        try {
            FileInputStream fis = new FileInputStream(".git/logs/" + hash);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Commit commit = (Commit) ois.readObject();
            ois.close();
            fis.close();
            return commit;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void checkout() {
        state.restore(".");
    }
}
