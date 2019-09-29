package core;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;

public class ScreenshotListener extends TestListenerAdapter {

//    private static final Logger logger = Logger.getLogger(ScreenshotListener.class);
//    private static final String TARGET_DIRECTORY = System.getProperty("target.dir");
//    private static final String DS = System.getProperty("file.separator");
//    private static final String SCREENSHOT_DIRECTORY = TARGET_DIRECTORY + DS + "screenshots";
//    private static final String SCREENSHOT_EXTENSION = ".png";

    @Override
    public void onTestFailure(ITestResult testResult) {
        getScreenShot();
//        byte[] screenshot = getScreenShot(BaseTest.getDriver());
//        if (nonNull(screenshot)) {
//            saveToFile(screenshot, getFileName(testResult.getName()));
//        } else {
//            logger.error("Something went wrong, screenshot was not taken");
//        }
    }

    @Attachment(value = "Screen shot", type = "image/png", fileExtension = ".png")
    private byte[] getScreenShot() {
        try {
//            return ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BYTES);
            return ((TakesScreenshot) TestEnvironment.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }

    @Attachment(value = "Screen shot", type = "image/png", fileExtension = ".png")
    private byte[] getScreenShot(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }

    private void saveToFile(byte[] bytes, String fileName) {
        try {
            FileUtils.writeByteArrayToFile(new File(fileName), bytes);
        } catch (IOException e) {
//            logger.error(String.format("There was an issue creating the screenshot %s: %s", fileName, e.getMessage()));
//            e.printStackTrace();
        }
    }

//    private static String getFileName(String methodName) {
//        return SCREENSHOT_DIRECTORY + DS + methodName + "_" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-sss").format(new Date()) + SCREENSHOT_EXTENSION;
//    }
}