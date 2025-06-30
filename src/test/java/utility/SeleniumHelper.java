//package utility;
//
//import com.google.inject.Inject;
//import org.junit.Assert;
//import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.FluentWait;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//
//public class SeleniumHelper {
//
//    WebDriver driver;
//    WebElement element;
//
//    @Inject
//    public SeleniumHelper(WebDriver driver){
//        this.driver=driver;
//    }
//
//    public boolean waitForElementToBeClickable(WebDriver driver, By attributeValue, int waitTime) {
//        boolean flag = false;
//        try{
//            new WebDriverWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
//                    .until(ExpectedConditions.elementToBeClickable(attributeValue));
//            flag=true;
//            return flag;
//
//        }catch(Exception Ex){
//            return flag;
//        }
//    }
//
//    public boolean waitForAlertPresent(WebDriver driver, int waitTime) {
//        boolean flag = false;
//        new WebDriverWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
//                .until(ExpectedConditions.alertIsPresent());
//        try{
//            driver.switchTo().alert();
//            return flag = true;
//        }catch(Exception Ex){
//            return flag;
//        }
//    }
//
//
//    public boolean waitForElementToBeVisible(WebDriver driver, By attributeValue, int waitTime) {
//        boolean flag = false;
//        try {
//            new WebDriverWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
//                    .until(ExpectedConditions.visibilityOfElementLocated(attributeValue));
//            flag=true;
//            return flag;
//        } catch (Exception Ex) {
//            return flag;
//        }
//    }
//
//    public void MouseClickActionMoveToElement(WebDriver driver, By attributeValue) {
//        try {
//            if
//
//            (waitForElementToBeClickable(driver, attributeValue, 60)) {
//                WebElement element = driver.findElement(attributeValue);
//                //element.click();
//                Actions builder = new Actions(driver);
//                builder.moveToElement(element).click().build().perform();
//                System.out.println("Able to locate and click to element !");
//
//            } else {
//                System.out.println("Not able to locate the element !");
//            }
//        } catch (Exception Ex) {
//            System.out.println("Exception occured");
//        }
//
//
//    }
//
//
//    public static int getcurrenttime() {
//        long currentDateMS = new Date().getTime();
//        int seconds = (int) ((currentDateMS / 1000) % 3600);
//        return seconds;
//    }
//
//    public String getText(WebElement element){
//            String txt = null;
//            try{
//                waitForElementToBeVisible(element);
//                txt = element.getText();
//                System.out.println("expected text : "+ txt);
//
//            }catch (NoAlertPresentException e){
//                System.out.println("No text is present");
//            }
//            return  txt;
//    }
//
//
//    public boolean waitForElementToBeVisible(WebElement element) {
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, 60);
//            wait.until(ExpectedConditions.visibilityOf(element));
//            return true;
//
//        } catch (Exception t) {
//            throw t;
//
//        }
//    }
//
//    public boolean isElementNotDisplayed(WebElement element) {
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, 10);
//            wait.until(ExpectedConditions.visibilityOf(element));
//            return false;
//
//        } catch (Exception t) {
//            System.out.println("Exception "+ t);
//            return true;
//
//        }
//    }
//
//
//    public boolean waitForElementToBeClickable(WebElement element) {
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, 60);
//            wait.until(ExpectedConditions.elementToBeClickable(element));
//            return true;
//
//        } catch (Exception t) {
//            throw t;
//
//        }
//    }
//
//    public boolean waitForElementToBeVisibleAndClickable(WebElement element) {
//
//        try {
//
//            return waitForElementToBeVisible(element)  && waitForElementToBeClickable(element);
//
//        } catch (Exception t) {
//            throw t;
//
//        }
//    }
//
//
//    public void waitForPageLoad() {
//        ExpectedCondition<Boolean> expectation = new
//                ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver driver) {
//                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
//                    }
//                };
//        try {
//            Thread.sleep(1000);
//            WebDriverWait wait = new WebDriverWait(driver, 30);
//            wait.until(expectation);
//        } catch (Throwable error) {
//            Assert.fail("Timeout waiting for Page Load Request to complete.");
//        }
//    }
//
////    public void waitForPageLoad(){
////
////        new WebDriverWait(driver, 60).until((ExpectedConditions <Boolean>) wd ->
////                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
////    }
//
//    public void waitTillClickableElementExist(WebElement element){
//        final Boolean[] isElementFound = new Boolean[1];
//        FluentWait<WebDriver> wait = new FluentWait<>(driver);
//        wait.pollingEvery(250, TimeUnit.MILLISECONDS);
//        wait.withTimeout(2,TimeUnit.MINUTES);
//        wait.ignoring(NoSuchElementException.class);
//
//        Function<WebDriver, Boolean> function = new com.google.common.base.Function<WebDriver,Boolean>(){
//            public Boolean apply(WebDriver arg){
//                if(isClickableElementExisting(element)){
//                    isElementFound[0] = true;
//                    return  true;
//                }
//                return false;
//            }
//        };
//        wait.until(function);
//        System.out.println("WaitTillTextExist method "+element + "status is : "+isElementFound[0]);
//
//    }
//
//
//
//    public Boolean isClickableElementExisting(WebElement element){
//
//        WebDriverWait wait = new WebDriverWait(driver,60);
//        wait.until(ExpectedConditions.elementToBeClickable(element));
//        return waitForElementToBeVisible(element);
//
//    }
//
//    public void enterText(WebElement element, String string){
//        try{
//            WebDriverWait wait =  new WebDriverWait(driver,60);
//            wait.until(ExpectedConditions.visibilityOf(element));
//
//            element.click();
//            element.clear();
//            element.sendKeys(string);
//            waitForPageLoad();
//        }catch (Exception t)
//        {
//            System.out.println("Unable to enter text in : "+element);
//            throw t;
//        }
//    }
//
//    public void click(WebElement element){
//        try{
//            WebDriverWait wait =  new WebDriverWait(driver,60);
//            wait.until(ExpectedConditions.visibilityOf(element));
//            waitTillClickableElementExist(element);
//            element.click();
//            waitForPageLoad();
//        }catch (Exception t)
//        {
//            System.out.println("Unable to click on Webelement : "+element);
//            throw t;
//        }
//    }
//
//
//    public void clickJS(WebElement element){
//       scrollViewUsingJS(element);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",element);
//    }
//
//
//    public void scrollViewUsingJS(WebElement element){
//        try {
//            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",element);
//        }catch (java.util.NoSuchElementException t)
//        {
//            throw t;
//        }
//    }
//
//    public void switchToWindow(String windowHandle){
//
//        driver.switchTo().window(windowHandle);
//    }
//
//    public void switchToDefaultFrame(){
//        try{
//            driver.switchTo().defaultContent();
//        }catch (Exception t){
//            System.out.println("Unable to switch to default farme");
//            throw t;
//        }
//
//
//    }
//
//
//
//
//
//}
