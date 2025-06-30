Feature: Login feature
@test
  Scenario: Verify login with valid credentials
    Given I launch the application
    When I login as "admin"
#    Then I should see the dashboard
