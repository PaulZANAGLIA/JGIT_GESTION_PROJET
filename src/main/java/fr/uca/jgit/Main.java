package fr.uca.jgit;

import fr.uca.jgit.model.Commit;
import fr.uca.jgit.model.Folder;
import fr.uca.jgit.model.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String HEAD_PATH = ".jgit/HEAD.txt";
        System.out.println(Arrays.toString(args));

        switch (args[0]){
            case "init":
                File jgit = new File(".jgit");
                File fo = new File(".jgit/objects");
                File fl = new File(".jgit/logs");
                File fh = new File(".jgit/HEAD.txt");

                if (jgit.exists()){
                    System.out.println("Directory already versioned");
                    System.exit(0);
                }
                try {
                    if (fo.mkdirs() && fh.createNewFile() && fl.mkdirs()) {
                        System.out.println("Directory created ");

                        // Créer un premier commit vide
                        Commit initialCommit = new Commit(new ArrayList<>(), new Folder(), "Initial commit");
                        initialCommit.store();

                        // Sauvegarder le hash dans le fichier head
                        FileWriter writer = new FileWriter(HEAD_PATH, false);
                        writer.write(initialCommit.hash());
                        writer.close();
                    } else {
                        System.out.println("Directory already created");
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "checkout":
                if (Objects.equals(args[1], "")){
                    System.err.println("You must specify the Hash of the branch");
                    System.exit(1);
                }
                break;
            case "commit":
                if( args.length < 2 || Objects.equals(args[1], "")){
                    System.err.println("You must specify a message for your commit");
                    System.exit(1);
                }
                String message = args[1];
                ArrayList<String> hashes = new ArrayList<>();
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(HEAD_PATH));
                    String line = reader.readLine();
                    while (line != null) {
                        hashes.add(line);
                        line = reader.readLine();
                    }
                    reader.close();

                    // Convert to Commit object
                    ArrayList<Commit> head = new ArrayList<>();
                    for(String h : hashes) head.add(Commit.loadCommit(h));


                    // Commit the new one
                    Folder tree = new Folder();
                    Commit newCommit = new Commit(head, tree, message);
                    // Browse the tree to find new nodes
                    Utils.buildTree(new File(".jgit/"), tree);

                    // Look at changes and add it
                    //...

                    newCommit.store();

                    // Save in HEAD file the last commit created
                    FileWriter writer = new FileWriter(HEAD_PATH, false);
                    writer.write(newCommit.hash());
                    writer.close();

                } catch (IOException e) {
                    System.err.println("A problem occurred when processing the commit");
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("invalid syntax");
                break;
        }
    }
}
