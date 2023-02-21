Feature: JGit commit test

  Scenario: Initialize JGit
    Given I have initialized JGit
    Then the .jgit directory should be created
    And the initial commit should be created

  Scenario: Create new commit
    Given I have created a new file "test.txt" with content "hello world"
    When I commit with message "Initial commit"
    Then a new commit should be created
