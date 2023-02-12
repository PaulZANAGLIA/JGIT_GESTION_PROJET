package fr.uca.jgit;

import fr.uca.jgit.model.Folder;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(Arrays.toString(args));
        if (args[0].equals("init")){
            File fo = new File(".jgit/object");
            File fh = new File(".jgit/HEAD");
            if (fo.mkdirs() && fh.mkdirs()) {
                System.out.println("Directory created ");
            }
        }
    }
}
