package fr.uca.jgit.model;
public interface Node extends JGitObject {
    /** Restores the file node at the given path. **/
    default String hash() {
        return Utils.hash(this.toString());
    }

    default void store() {
        Utils.store(this);
    }

    void restore(String path);
}