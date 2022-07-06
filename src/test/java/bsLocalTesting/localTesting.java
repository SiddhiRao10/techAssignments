package bsLocalTesting;

//Sample test in Java to run Automate session.
import bsDemoCommon.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.browserstack.local.Local;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class localTesting  {
    static Local bsLocal = new Local();
    public static WebDriver driver;
    public static final Logger logger = Logger.getLogger(String.valueOf(BasePO.class));
    DesiredCapabilities capabilities = new DesiredCapabilities();
    static String username = System.getenv("BROWSERSTACK_USERNAME");
    public static String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    static String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
    public static final String URL = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

@BeforeMethod
    public void setup() throws Exception {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("os", "OS X");
    caps.setCapability("os_version", "Sierra");
    caps.setCapability("browser", "Safari");
    caps.setCapability("browser_version", "10.0");
    caps.setCapability("browserstack.local", "true");
    caps.setCapability("name", "Local Test"); // test name
    caps.setCapability("build", buildName); // CI/CD job or build name
    caps.setCapability("browserstack.debug", "true");
    caps.setCapability("browserstack.networkLogs", "true");

    HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
    bsLocalArgs.put("key", accessKey);

    // Starts the Local instance with the required arguments
    bsLocal.start(bsLocalArgs);

    System.out.println(bsLocal.isRunning());
    driver = new RemoteWebDriver(new URL(URL), caps);
}
@Test
        public void localTest() {
    driver.get("http://localhost:8080/");
}
@AfterMethod
public void teardown() throws Exception {
            final WebDriverWait wait = new WebDriverWait(driver, 10);
            String bodyText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).getText();
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            if (bodyText.equals("Welcome to Jenkins!Keep me signed inSign in")) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test Passed\"}}");
            } else
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Test Failed\"}}");
            bsLocal.stop();
            driver.quit();
        }
    }
