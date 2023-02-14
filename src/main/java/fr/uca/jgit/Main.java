package fr.uca.jgit;

import fr.uca.jgit.model.Commit;
import fr.uca.jgit.model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
                break;
            default:
                System.out.println("invalid syntax");
                break;
        }
    }
}
