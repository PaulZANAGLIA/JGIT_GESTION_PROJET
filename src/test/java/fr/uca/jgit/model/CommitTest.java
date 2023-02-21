package fr.uca.jgit.model;

import fr.uca.jgit.Main;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommitTest {

    @Given("I have initialized JGit")
    public void i_have_initialized_jgit() {
        String[] args = {"init"};
        Main.main(args);
    }

    @Then("the .jgit directory should be created")
    public void the_jgit_directory_should_be_created() {
        File jgit = new File(".jgit");
        assertTrue(jgit.exists());
    }

    @Then("the initial commit should be created")
    public void the_initial_commit_should_be_created() {
        String HEAD_PATH = ".jgit/HEAD.txt";
        ArrayList<String> hashes = new ArrayList<>();
        try {
            java.util.Scanner s = new java.util.Scanner(new File(HEAD_PATH)).useDelimiter("\\n");
            while (s.hasNext()) {
                hashes.add(s.next());
            }
            s.close();
        } catch (IOException e) {
            System.err.println("A problem occurred when processing the commit");
            throw new RuntimeException(e);
        }
        assertEquals(hashes.size(), 1);
    }

    @Given("I have created a new file {string} with content {string}")
    public void i_have_created_a_new_file_with_content(String filename, String content) {
        File testFile = new File(filename);
        try {
            FileWriter writer = new FileWriter(testFile);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error creating file");
            throw new RuntimeException(e);
        }
    }

    @When("I commit with message {string}")
    public void i_commit_with_message(String message) {
        String[] args = {"commit", message};
        Main.main(args);
    }

    @Then("a new commit should be created")
    public void a_new_commit_should_be_created() {
        String HEAD_PATH = ".jgit/HEAD.txt";
        ArrayList<String> hashes = new ArrayList<>();
        try {
            java.util.Scanner s = new java.util.Scanner(new File(HEAD_PATH)).useDelimiter("\\n");
            while (s.hasNext()) {
                hashes.add(s.next());
            }
            s.close();
        } catch (IOException e) {
            System.err.println("A problem occurred when processing the commit");
            throw new RuntimeException(e);
        }
        assertEquals(hashes.size(), 1);
    }
}

