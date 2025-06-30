package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String scenarioName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String folder = Paths.get("Reports", "screenshots").toString() + File.separator;

            new File(folder).mkdirs();
            String dest = folder + scenarioName + "_" + timestamp() + ".png";
            FileUtils.copyFile(source, new File(dest));
            return dest;
        } catch (Exception e) {
            return null;
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }


}
