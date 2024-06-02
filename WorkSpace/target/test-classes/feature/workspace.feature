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

  Scenario: Get the newly created workspace
    When Pass the body along with the newly create id
    Then validate the status code 200 or not
    And validate the body
