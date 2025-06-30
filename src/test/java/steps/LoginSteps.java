package steps;

import io.cucumber.java.en.*;
import junit.framework.Assert;
import utility.ConfigReader;
import utility.DriverFactory;
import pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("I launch the application")
    public void i_launch_the_application() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getAppUrl());

    }

    @When("I login as {string}")
    public void i_login_as(String role) {
        String[] creds = ConfigReader.getCredentials(role);
        if (creds == null) {
            Assert.fail("No credentials found for role: " + role);
        }
        loginPage = new LoginPage(driver);
        loginPage.login(creds[0], creds[1]);
    }

    @Then("I should see the dashboard")
    public void i_should_see_dashboard() {
        // assertions here
    }
}
