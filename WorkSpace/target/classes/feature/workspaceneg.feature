@NegitiveScenarios
Feature: Negitive scenarios for postman workspace

  Background: 
    
    Given: Enter the baseURL


  Scenario: To get the workspace with wrong id
    When post worng workspace id along with the url
    Then check the status code 404
    And check the error message and key are displayed

  Scenario Outline: To create a workspace with wrong type
    When Send the body with wrong type "<name>" "<type>" "<description>"
    Then check the status code 400 or not
    Then validate the body of the data

    Examples: 
      | name   | type | description    |
      | Aditya | QA   | Hello Practice |

  Scenario: Try to delete workspace with invalid id
    When Send the body with invalid id
    Then check the response code 404
    And validate the error message

  Scenario: Check the error meassage when given a wrong URL
    When Send the wrong URL in path
    Then Check the status code along with error message
