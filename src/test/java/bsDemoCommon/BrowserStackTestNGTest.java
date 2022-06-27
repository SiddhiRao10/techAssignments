package bsDemoCommon;


import net.bytebuddy.asm.Advice;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Parameters;

import static bsDemoCommon.BasePO.readPropertiesFile;
public class BrowserStackTestNGTest {
    public WebDriver driver;
    public static final Logger logger = Logger.getLogger(String.valueOf(BasePO.class));
    DesiredCapabilities capabilities = new DesiredCapabilities();
    String username = readPropertiesFile("browserStackUserName");
    String accessKey = readPropertiesFile("browserStackPassword");



    @SuppressWarnings("unchecked")
    @BeforeMethod(alwaysRun = true)
    @Parameters(value = {"config", "environment"})
    public void setUp(String config_file, String environment) throws Exception {

        File name= new File("src/test/resources/" + config_file);
        if(!(name.equals("parallel.bsLiveInception.conf.json"))) {
            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/" + config_file));
            JSONObject envs = (JSONObject) config.get("environments");

            Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
            Iterator it = envCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }

            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
            it = commonCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (capabilities.getCapability(pair.getKey().toString()) == null) {
                    capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                }
            }



            if (username == null) {
                username = (String) config.get("user");
            }


            if (accessKey == null) {
                accessKey = (String) config.get("key");
            }

       /* if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }*/

            driver = new RemoteWebDriver(
                    new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();

    }
    }

