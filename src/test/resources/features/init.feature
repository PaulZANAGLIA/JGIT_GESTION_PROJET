Feature: jgit init

  Background:
    Given a folder "repo" and with command init

  Scenario: init is made
    Then folder "repo" exist with inside file HEAD, folder objects and folder logs