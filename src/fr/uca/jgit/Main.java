package fr.uca.jgit;

import fr.uca.jgit.model.Folder;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(Arrays.toString(args));
        if (args[0].equals("init")){
            File fo = new File(".jgit/objects");
            File fl = new File(".jgit/logs");
            File fh = new File(".jgit/HEAD.txt");
            if (fo.mkdirs() && fh.mkdirs() && fl.mkdirs()) {
                System.out.println("Directory created ");
            } else {
                System.out.println("Directory already created");
            }
        }
    }
}
