package bsDemoCommon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import bsDemoCommon.BasePO;

import static bsDemoCommon.BasePO.readPropertiesFile;

public class BaseTest {
    public  String USERNAME = readPropertiesFile("browserStackUserName");;
    public  String AUTOMATE_KEY = readPropertiesFile("browserStackPassword");
    public  final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final Logger logger = Logger.getLogger(String.valueOf(BasePO.class));
    public WebDriver driver;

   //@Parameters({"browser","browser_version","os","os_version"})
   @BeforeTest
  // public void setup(String browser, String browser_version, String os, String os_version) throws MalformedURLException, MalformedURLException {
   public void setup(){
   /*    if(readPropertiesFile("runOn").equals("BrowserStack"))
        {
            DesiredCapabilities caps=new DesiredCapabilities();
            caps.setCapability("browser",browser);
            caps.setCapability("browser_version",browser_version);
            caps.setCapability("os",os);
            caps.setCapability("os_version",os_version);

            java.net.URL browserURL=new URL(URL);
            driver=new RemoteWebDriver(browserURL,caps);
        }
        else
        {*/
            System.setProperty("webdriver.chrome.driver","/Users/siddhirao/IdeaProjects/automateAssignments/src/main/resources/drivers/chromedriver");
            // System.setProperty("webdriver.gecko.driver","/Users/siddhirao/IdeaProjects/automateAssignments/src/main/resources/drivers/geckodriver");
            //   driver =new FirefoxDriver();
            driver=new ChromeDriver();
      //  }
    }

    @AfterTest
    public void tearDown()
    {
        driver.quit();

    }
}
