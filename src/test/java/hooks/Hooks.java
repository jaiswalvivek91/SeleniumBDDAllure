package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utility.DriverFactory;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();

    }
}
