Feature: This feature will validate flight booking functionality


  Scenario Outline: Verify flight booking for users
    Given User navigates to flight booking application
    When user selects "<departure>" and "<destination>" city
    And click on Find Flights button
    And user choose flight to reserve ticket
    And user enters all passenger details
    And clicks on Purchase Flight button
    Then user validates purchase details
  Examples:
      | departure   | destination |
      | Paris       | Rome        |
      | Boston      | Berlin      |

  @Sanity
  Scenario Outline: Verify departure and destination dropdowns have all expected cities
    Given User navigates to flight booking application
    Then User validates "<departure>" and "<destination>" cities available
  Examples:
    | departure                                                         | destination                                           |
    | Paris,Philadelphia,Boston,Portland,San Diego,Mexico City,São Paolo| Buenos Aires,Rome,London,Berlin,New York,Dublin,Cairo |

  @Sanity
  Scenario Outline: Verify flight header message is displayed in FLights page
    Given User navigates to flight booking application
    When user selects "<departure>" and "<destination>" city
    And user choose flight to reserve ticket
    Then user validates flight header message "<departure>" and "<destination>" city
  Examples:
      | departure   | destination |
      | Paris       | Rome        |
      | Boston      | Berlin      |





