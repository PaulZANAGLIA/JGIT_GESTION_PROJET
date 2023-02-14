package fr.uca.jgit.model;

import static org.junit.jupiter.api.Assertions.*;

import fr.uca.jgit.Main;
import io.cucumber.java.en.*;

import java.io.File;

public class InitTest {

    @Given("a folder {string} and with command init")
    public void initTest(String str){
        Main.main(new String[]{"init"});
    }

    @Then("folder {string} exist with inside file HEAD, folder objects and folder logs")
    public void thenExists(String str){
        File fh = new File(".jgit/HEAD.txt");
        File fo = new File(".jgit/objects");
        File fl = new File(".jgit/logs");
        assertTrue(fh.exists() && fo.exists() && fl.exists());
    }
}
