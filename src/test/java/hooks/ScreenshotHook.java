package hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utility.DriverFactory;
import utility.ScreenshotUtil;
import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScreenshotHook {

    @AfterStep
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, scenario.getName());
                if (screenshotPath != null) {
                    try {
                        Allure.addAttachment("❌ Screenshot on Failure", new FileInputStream(screenshotPath));
                    } catch (FileNotFoundException e) {
                        System.err.println("⚠️ Failed to attach screenshot: " + e.getMessage());
                    }
                }
            }
        }
    }
}
