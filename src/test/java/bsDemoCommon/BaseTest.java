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

}
