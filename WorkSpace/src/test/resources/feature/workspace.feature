@PositiveScenarios
Feature: Workspace

  Background: 
    Given Enter the baseURL

  Scenario: To get the list of workspaces
  When pass the endpoint
  Then validate the status code
  And Check the body
  
  Scenario Outline: To create a workspace
  When Send the body with "<name>" "<type>" "<description>"
  Then validate the status code 200
  Then validate the body data
  
  Examples:
  | name   | type     | description    |
  | Aditya | personal | Hello Practice |
  
  Scenario Outline: Get the newly created workspace
  When Pass the body along with the newly create id
  Then validate the status code 200 or not
  And validate the body
  Scenario: To update the workspace
    When Pass the body along with the new data
      | name           | type | description               |
      | AdityaTeja | personal | QA |
    Then validate the status code and body
    
  Scenario: Delete the newly created workspace
    When Send the body along with id to delete the data
    Then check the status code 200 or nott
    And get the body again and validate the code
