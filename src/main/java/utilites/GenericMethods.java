package utilites;

import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import logger.Log;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.touch.offset.PointOption;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Sheet;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class GenericMethods {

    public static WebDriver driver = null;
    public static String executionComments = null;
    protected static File file = new File("");
    // common timeout for all tests can be set here
    public final int timeOut = 20000;
    public static Dimension screenSize;
    public static int screenHeight;
    public static int screenWidth;
    private static final long MINIMUM_WAIT_TIME = 1000; // Minimum wait time in milliseconds
    private static final long MEDIUM_WAIT_TIME = 2000;
    private static final long MAXIMUM_WAIT_TIME = 5000; // Maximum wait time in milliseconds
   // private static final Logger logger = (Logger) LogManager.getLogger(GenericMethods.class);


    public static String sTestDataFile = file.getAbsoluteFile()+"\\TestData.xlsx";

    public GenericMethods(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public Boolean isElementPresent(By targetElement) throws InterruptedException {
        Boolean isPresent = driver.findElements(targetElement).size() > 0;
        return isPresent;
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * method to hide keyboard
     */
//     public void hideKeyboard() {
//        ((AppiumDriver) driver).hideKeyboard();
//    }

    /**
     * method to go back by Android Native back click
     */
    public void back() {
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    /**
     * method to wait for an element to be visible
     *
     * @param targetElement element to be visible
     * @return true if element is visible else throws TimeoutException
     */
    public boolean waitForVisibility(By targetElement) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, ofMillis(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not visible: " + targetElement);
            throw e;

        }
    }


    public WebElement waitForVisibilityOfElement(WebElement targetElement) throws InterruptedException {
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, ofMillis(timeOut));
            WebElement wb=wait.until(ExpectedConditions.visibilityOf(targetElement));
            return wb;
        } catch (TimeoutException | InterruptedException e) {
            System.out.println("Element is not visible: " + targetElement);
            throw e;

        }
    }

    /**
     * method to wait for an element until it is invisible
     *
     * @param targetElement element to be invisible
     * @return true if element gets invisible else throws TimeoutException
     */
    public boolean waitForInvisibility(By targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, ofMillis(timeOut));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is still visible: " + targetElement);
            System.out.println();
            System.out.println(e.getMessage());
            throw e;

        }
    }

    /**
     * method to tap on the screen on provided coordinates
     *
     * @param xPosition x coordinate to be tapped
     * @param yPosition y coordinate to be tapped
     */
    public void tap(double xPosition, double yPosition) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("startX", xPosition);
        tapObject.put("startY", yPosition);
        js.executeScript("mobile: tap", tapObject);
    }


    /**
     * method to find an element
     *
     * @param locator element to be found
     * @return WebElement if found else throws NoSuchElementException
     */
    public WebElement findElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element;
        } catch (NoSuchElementException e) {
            Log.logError(this.getClass().getName(), "findElement", "Element not found" + locator);
            throw e;
        }
    }

    /**
     * method to find all the elements of specific locator
     *
     * @param locator element to be found
     * @return return the list of elements if found else throws NoSuchElementException
     */
    public List<WebElement> findElements(By locator) {
        try {
            List<WebElement> element = driver.findElements(locator);
            return element;
        } catch (NoSuchElementException e) {
        	Log.logError(this.getClass().getName(), "findElements", "element not found" + locator);
            throw e;
        }
    }

    /**
     * method to get message test of alert
     *
     * @return message text which is displayed
     */
    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            return alertText;
        } catch (NoAlertPresentException e) {
            throw e;
        }
    }

    /**
     * method to verify if alert is present
     *
     * @return returns true if alert is present else false
     */
    public boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            throw e;
        }
    }

    /**
     * method to Accept Alert if alert is present
     */

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * method to Dismiss Alert if alert is present
     */

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    /**
     * method to get network settings
     */
    public void getNetworkConnection() {
        System.out.println(((AndroidDriver) driver).getConnection());
    }


    /**
     * method to set network settings
     *
     * @param airplaneMode pass true to activate airplane mode else false
     * @param wifi         pass true to activate wifi mode else false
     * @param data         pass true to activate data mode else false
     */
    public void setNetworkConnection(boolean airplaneMode, boolean wifi, boolean data) {

        long mode = 1L;

        if (wifi) {
            mode = 2L;
        } else if (data) {
            mode = 4L;
        }

        ConnectionState connectionState = new ConnectionState(mode);
        ((AndroidDriver) driver).setConnection(connectionState);
        System.out.println("Your current connection settings are :" + ((AndroidDriver) driver).getConnection());
    }


    /**
     * method to get all the context handles at particular screen
     */
//    public void getContext() {
//        ((AppiumDriver) driver).getContextHandles();
//    }

    /**
     * method to set the context to required view.
     *
     * @param context view to be set
     */
//    public void setContext(String context) {
//
//        Set<String> contextNames = ((AppiumDriver) driver).getContextHandles();
//
//        if (contextNames.contains(context)) {
//            ((AppiumDriver) driver).context(context);
//            Log.info("Context changed successfully");
//        } else {
//            Log.info(context + "not found on this page");
//        }
//
//        Log.info("Current context" + ((AppiumDriver) driver).getContext());
//    }

    /**
     * method to long press on specific element by passing locator
     *
     * @param locator element to be long pressed
     */
    public void longPress(By locator) {
        try {
            WebElement element = driver.findElement(locator);

            //TouchAction touch = new TouchAction((MobileDriver) driver);
            TouchAction touch = new TouchAction((PerformsTouchActions) driver);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            Log.info("Long press successful on element: " + element);
        } catch (NoSuchElementException e) {
        	Log.logError(this.getClass().getName(), "findElement", "Element not found" + locator);
            throw e;
        }

    }

    /**
     * method to long press on specific x,y coordinates
     *
     * @param x x offset
     * @param y y offset
     */
    public void longPress(int x, int y) {
        //TouchAction touch = new TouchAction((MobileDriver) driver);
        TouchAction touch = new TouchAction((PerformsTouchActions) driver);
        PointOption pointOption = new PointOption();
        pointOption.withCoordinates(x, y);
        touch.longPress(pointOption).release().perform();
        Log.info("Long press successful on coordinates: " + "( " + x + "," + y + " )");

    }

    /**
     * method to long press on element with absolute coordinates.
     *
     * @param locator element to be long pressed
     * @param x       x offset
     * @param y       y offset
     */
    public void longPress(By locator, int x, int y) {
        try {
            WebElement element = driver.findElement(locator);
            //TouchAction touch = new TouchAction((MobileDriver) driver);
            TouchAction touch = new TouchAction((PerformsTouchActions) driver);
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withPosition(new PointOption().withCoordinates(x, y)).withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            Log.info("Long press successful on element: " + element + "on coordinates" + "( " + x + "," + y + " )");
        } catch (NoSuchElementException e) {
        	Log.logError(this.getClass().getName(), "findElement", "Element not found" + locator);
            throw e;
        }

    }

    /**
     * method to swipe on the screen on provided coordinates
     *
     * @param startX   - start X coordinate to be tapped
     * @param endX     - end X coordinate to be tapped
     * @param startY   - start y coordinate to be tapped
     * @param endY     - end Y coordinate to be tapped
     * @param duration duration to be tapped
     */

    public void swipe(double startX, double startY, double endX, double endY, double duration) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        // swipeObject.put("touchCount", 1.0);
        swipeObject.put("startX", startX);
        swipeObject.put("startY", startY);
        swipeObject.put("endX", endX);
        swipeObject.put("endY", endY);
        swipeObject.put("duration", duration);
        js.executeScript("mobile: swipe", swipeObject);
    }


    static String UiScrollable(String uiSelector) {
        return "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + uiSelector + ".instance(0));";
    }

    /**
     * method to open notifications on Android
     */

    public void openNotifications() {
        ((AndroidDriver) driver).openNotifications();
    }


    /**
     * method to launchApp
     */

//    public void launchApp() {
//        ((AppiumDriver) driver).launchApp();
//
//    }


    /**
     * method to click on Element By Name
     *
     * @param elementByName - String element name to be clicked
     */

    public void click(String elementByName) {

        ((AppiumDriver) driver).findElement(By.name(elementByName)).click();
    }

    /**
     * method to scroll down on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollDown(int swipeTimes, int durationForSwipe) {
        Dimension dimension = driver.manage().window().getSize();

        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.5);
            int end = (int) (dimension.getHeight() * 0.3);
            int x = (int) (dimension.getWidth() * .5);


            new TouchAction((PerformsTouchActions) driver)
                    .press(point(x, start))
                    .moveTo(point(x, end))
                    .waitAction(waitOptions(ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }


    /**
     * method to scroll up on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollUp(int swipeTimes, int durationForSwipe) {
        Dimension dimension = driver.manage().window().getSize();

        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.3);
            int end = (int) (dimension.getHeight() * 0.5);
            int x = (int) (dimension.getWidth() * .5);


            new TouchAction((PerformsTouchActions) driver).press(point(x, start)).moveTo(point(x, end))
                    .waitAction(waitOptions(ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }

    public static String[] toReadExcelData(String sSheet , String sTestCaseID) throws EncryptedDocumentException, InvalidFormatException, IOException, IOException, InvalidFormatException {
        String[] sData = null;
        FileInputStream fis = new FileInputStream(sTestDataFile);
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sht = wb.getSheet(sSheet);
        int rowNum = sht.getLastRowNum();

        for (int i = 1; i<=rowNum ; i++)
        {
            if(sht.getRow(i).getCell(0).toString().equals(sTestCaseID))
            {
                int cellNum = sht.getRow(i).getLastCellNum();
                sData = new String[cellNum-1];

                for(int j = 1; j<cellNum ; j++)
                {
                    sData[j-1] = sht.getRow(i).getCell(j).getStringCellValue();
                }

                break;
            }
        }
        return sData;
    }

    public WebElement getElementByValue(String value) {
        String xpath = "//*[contains(@content-desc,'" + value + "')]";
        return driver.findElement(By.xpath(xpath));

    }


    public static void swipeRightToLeft(double startX , double endX)
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        int starty = screenHeight/2;
        int startx = (int) (screenWidth*startX);
        int endx = (int) (screenWidth*endX);
        //driver.swipe(startx, starty, endx, starty, 2000);
//        new TouchAction(driver)
//                .Press(point(startx, starty))
//                .waitAction(WaitOptions(ofMillis(3000))
//                .moveTo(point(endx, starty))
//                .release()
//                .perform();

        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(endx, starty))
                .release().perform();
    }


    public static void swipeLeftToRight(double startX , double endX )
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;

        int starty = screenHeight/2;
        int startx = (int) (screenWidth*startX);
        int endx = (int) (screenWidth*endX);
        //BaseLibrary.driver.swipe(endx, starty, startx, starty, 3000);
//        new TouchAction((PerformsTouchActions) driver)
//                .longPress(point(startx, starty))
//                .waitAction(waitOptions(ofMillis(3000))
//                .moveTo(point(endx,starty ))
//                .release()
//                .perform();

        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(endx, starty))
                .release().perform();
    }



    public static void swipeBottomToTop(double startY , double endY)
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;

        int startx = screenWidth/2;
        int starty = (int) (screenHeight*startY);
        int endy = (int) (screenHeight*endY);
        //BaseLibrary.driver.swipe(startx, starty, startx, endy, 3000);
//        new TouchAction((PerformsTouchActions) driver)
//                .longPress(point(startx, starty))
//                .waitAction(waitOptions(ofMillis(3000))
//                .moveTo(point(startx, endy))
//                .release()
//                .perform();
        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(startx, endy))
                .release().perform();
    }


    public static void swipeTopToBottom(double startY , double endY)
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;

        int startx = screenWidth/2;
        int starty = (int) (screenHeight*startY);
        int endy = (int) (screenHeight*endY);
        //BaseLibrary.driver.swipe(startx, endy, startx, starty, 3000);
//        new TouchAction((PerformsTouchActions) driver)
//                .longPress(point(startx, starty))
//                .waitAction(waitOptions(ofMillis(3000))
//                .moveTo(point(startx,endy ))
//                .release()
//                .perform();
        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(startx, endy))
                .release().perform();
    }

    public static void minimumWait() {
        try {
            Thread.sleep(MINIMUM_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mediumWait() {
        try {
            Thread.sleep(MEDIUM_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void maximumWait() {
        try {
            Thread.sleep(MAXIMUM_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitInSeconds(long seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitInMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(AppiumDriver driver) {
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).hideKeyboard();
            //((AndroidDriver) driver).navigate().back();
        } else if (driver instanceof IOSDriver) {
            try {
                ((IOSDriver) driver).hideKeyboard();
              //  ((IOSDriver) driver).navigate().back();
            } catch (Exception e) {
                // Handle any exception thrown by hideKeyboard() on iOS
                System.out.println("Failed to hide keyboard: " + e.getMessage());
            }
        } else {
            System.out.println("Unsupported driver type");
        }
    }

    public static void navigateBack(AppiumDriver driver) {
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).navigate().back();
        } else if (driver instanceof IOSDriver) {
            try {
                ((IOSDriver) driver).navigate().back();
            } catch (Exception e) {
                // Handle any exception thrown by hideKeyboard() on iOS
                System.out.println("Failed to navigate back: " + e.getMessage());
            }
        } else {
            System.out.println("Unsupported driver type");
        }
    }

    public static void clickIfElementIsPresent(WebElement element) {
        try {
            //WebElement element = driver.findElement(locator);
            Thread.sleep(1000);
            element.click();
        } catch (NoSuchElementException | InterruptedException e) {
            // Element not found, test fails
        	Log.info("Element is not clicked 'clickIfElementIsPresent' ");
            //Assert.fail("Element not found: " + locator);
        }
    }

    public static void swipeLeftToRightByLeavingPercentageScreenFromTop(double startX , double endX , double percentageLeft )
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;

        int starty = (int) (screenHeight*percentageLeft);
        int startx = (int) (screenWidth*startX);
        int endx = (int) (screenWidth*endX);
        //BaseLibrary.driver.swipe(endx, starty, startx, starty, 3000);
//        new TouchAction((PerformsTouchActions) driver)
//                .longPress(point(startx, starty))
//                .waitAction(waitOptions(ofMillis(3000))
//                .moveTo(point(endx,starty ))
//                .release()
//                .perform();

        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(endx, starty))
                .release().perform();
    }

    public static void swipeRightToLeftByLeavingPercentageScreenFromTop(double startX , double endX, double percentageLeft)
    {
        //driver = BaseLibrary.driver;
        screenSize = driver.manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        int starty = (int) (screenHeight*percentageLeft);
        int startx = (int) (screenWidth*startX);
        int endx = (int) (screenWidth*endX);
        //driver.swipe(startx, starty, endx, starty, 2000);
//        new TouchAction(driver)
//                .Press(point(startx, starty))
//                .waitAction(WaitOptions(ofMillis(3000))
//                .moveTo(point(endx, starty))
//                .release()
//                .perform();

        new TouchAction((PerformsTouchActions) driver)
                .press(point(startx, starty))
                .waitAction(waitOptions(ofMillis(3000)))
                .moveTo(point(endx, starty))
                .release().perform();
    }
    
    public String captureScreenShot()throws Exception
    {
 	   SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
 	   Date d=new Date();
 	   String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(d)+".png";
 	   File dest=new File(fn);
 	   File src=((AppiumDriver) driver).getScreenshotAs(OutputType.FILE);
 	   FileHandler.copy(src,dest);
 	   return(dest.getAbsolutePath());
    }



}

