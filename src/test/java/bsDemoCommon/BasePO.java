package bsDemoCommon;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BasePO {
    public static final Logger logger = Logger.getLogger(String.valueOf(BasePO.class));
    protected WebDriver driver;
    int status;
    public BasePO(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    // All page objects //
    @FindBy(xpath="//*[@id='signin']")
    public WebElement signIn;

    @FindBy(xpath="//button[@id='login-btn']")
    public WebElement login;

    @FindBy(xpath="//body/div[@id='__next']/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]")
    public WebElement username;

    @FindBy(xpath="//*[@id='password']")
    public WebElement password;

    @FindBy(xpath="//div[contains(text(),'Checkout')]")
    public WebElement checkout;

    @FindBy(xpath="//*[@class='form-legend-container']")
    public WebElement shippingDetailsScreen;

    @FindBy(xpath="//*[@id='firstNameInput']")
    public WebElement firstName;

    @FindBy(xpath="//*[@id='lastNameInput']")
    public WebElement lastName;

    @FindBy(xpath="//*[@id='addressLine1Input']")
    public WebElement address;

    @FindBy(xpath="//*[@id='provinceInput']")
    public WebElement province;

    @FindBy(xpath="//*[@id='postCodeInput']")
    public WebElement postCodeInput;

    @FindBy(xpath="//*[@id='checkout-shipping-continue']")
    public WebElement submit;

    @FindBy(xpath="//*[@id='confirmation-message']")
    public WebElement orderStatus;

    @FindBy(xpath ="//*[@id='nav-search-submit-button']")
    public WebElement searchClick;

    @FindBy(xpath="//input[@id='twotabsearchtextbox']")
    public WebElement searchBox;

    @FindBy(xpath="//*[@class='autocomplete-results-container']")
    public WebElement amazonSearchSuggestions;

    @FindBy(xpath ="//*[@class='a-section a-spacing-small a-spacing-top-small']//span[contains(text(),\"result\")]")
    public WebElement searchedResult;

    @FindBy(xpath="//ul//li[@aria-label=\"iOS\"]")
    public WebElement osFilter;

    @FindBy(xpath="//span[contains(text(),'results for')]")
    public WebElement filteredResult;

    @FindBy(xpath="//*//ul//li[@class='sign-in-link hide-xs hide-sm']//a[@title=\"Sign In\"]")
    public WebElement bsSignIn;

    @FindBy(xpath="//input[@id='user_email_login']")
    public WebElement bsUserName;

    @FindBy(xpath="//input[@id='user_password']")
    public WebElement bsUserPassword;

    @FindBy(xpath="//input[@id='user_submit']")
    public WebElement bsUserSubmit;

    @FindBy(xpath="//*//a[@class='header__product-name' and contains(text(),'Live')]")
    public WebElement liveModule;

    @FindBy(xpath="//div[@id='platform-list-react']")
    public WebElement liveDashboard;

    @FindBy(xpath="//*[@class='toolbar__head']")
    public WebElement liveSessionToolbar;

    public static String readPropertiesFile(String propertyName)
    {
        Properties prop= new Properties();
        try
        {
            prop.load(new FileInputStream(System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"readProperties"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(propertyName);
    }

    public void launchStackURL() {
        driver.get(readPropertiesFile("bStackDemoURL"));
    }

    public void searchProductAddCart() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

        List<WebElement> products=driver.findElements(By.xpath("//*[@class='shelf-container']//div[@class='shelf-item']//p"));
        List<WebElement> addCart=driver.findElements(By.xpath("//*[@class='shelf-container']//div[@class='shelf-item']//p/following-sibling::div/following-sibling::div"));

        for (int i = 0; i<products.size(); i++)
        {
            if (products.get(i).getText().equals("iPhone 12"))
            {
                logger.info("product found "+ products.get(i).getText());
                addCart.get(i).click();
                break;
            }
            else {
                logger.info("product not found ");
            }
        }
    }
    public void login() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        signIn.click();

        username.click();
        Actions act = new Actions(driver);
        act.sendKeys("demouser"+Keys.TAB).build().perform();

        password.click();
        act.sendKeys("testingisfun99"+Keys.TAB).build().perform();

        login.click();
    }
    public void checkout() {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        checkout.click();
    }
    public void shippingAddressDetails() {
        if(shippingDetailsScreen.isDisplayed())
        {
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
            logger.info("User needs to fill shipping address details");
            firstName.sendKeys(readPropertiesFile("shippingFirstName"));
            lastName.sendKeys(readPropertiesFile("shippingLastName"));
            address.sendKeys(readPropertiesFile("shippingAddress"));
            province.sendKeys(readPropertiesFile("shippingProvience"));
            postCodeInput.sendKeys(readPropertiesFile("shippingPostalCode"));

            submit.click();
        }
        else
            logger.info("Shipping address details screen is not shown");
    }

    public int orderStatus() {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        if(orderStatus.isDisplayed()) {
            if(orderStatus.getText().equals("Your Order has been successfully placed.")) {
                logger.info("Your Order has been successfully placed.");
                status = 1;
            }
        }
        else {
            logger.info("order has not been placed successfully");
            status=0;
        }
        return status;
    }

    public void launchAmazonURL() {
        driver.get(readPropertiesFile("amazonDemoURL"));
    }

    public void amazonSearchProduct() throws InterruptedException {
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        searchBox.sendKeys(readPropertiesFile("amazonProductSearch"));
        searchListSelection();
        searchClick.click();
    }

    private void searchListSelection() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        if (amazonSearchSuggestions.isDisplayed()) {
            logger.info(("Amazon search list is displayed"));
            List<WebElement> list = driver.findElements(By.xpath("//*[@class='autocomplete-results-container']//div"));

            logger.info("Total suggestion :" + list.size());

            String expectedSearch = readPropertiesFile("amazonProductSearch");

            for (int ls = 0; ls < list.size(); ls++) {
                if (list.get(ls).equals(expectedSearch)) {
                    list.get(ls).click();
                    break;
                }
            }
        }
        else
        {
            logger.info("no search");
        }
    }

    public void searchedPageFilter() throws InterruptedException {

        logger.info("**** total searched result before filter apply*** "+ searchedResult.getText() +"iPhone X");
        logger.info("Selecting Cell Phone Operating System filter");
        osFilter();
        logger.info("Selecting price filter");
        sortPrice();
    }

    private void osFilter() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,750)", "");
        Thread.sleep(2000);
        try {
            if(osFilter.isDisplayed()) {
                osFilter.click();
            }
        }catch (NoSuchElementException e){
            logger.info(String.valueOf(e));
        }

    }

    private void sortPrice() throws InterruptedException {
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-750)", "");
        Select drpCountry = new Select(driver.findElement(By.id("s-result-sort-select")));
        drpCountry.selectByVisibleText("Price: High to Low");
        Thread.sleep(3000);
    }

    public int resultPage() throws InterruptedException {

        logger.info("Total product on final result page** " + filteredResult.getText());

        List<WebElement> productName = driver.findElements(By.xpath("//*[@class='s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16']//h2"));
        List<WebElement> productPrice = driver.findElements(By.xpath("//*[@class='s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16']//a//span[@class='a-price']"));
        List<WebElement> productLink = driver.findElements(By.xpath("//*[@class='s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16']//h2//a"));

        logger.info(String.valueOf(productName.size()));
        logger.info(String.valueOf(productPrice.size()));
        logger.info(String.valueOf(productLink.size()));
        try {
            for (int i = 0; i < productName.size(); i++) {

                logger.info("Product Name : " + productName.get(i).getText() + " **** " + " Product Price : " + productPrice.get(i).getText() + " ***** " + " Product Link : " + productLink.get(i).getAttribute("href"));
            }

            Thread.sleep(3000);
        } catch (Exception e) {
            logger.info(String.valueOf(e));
        }
        status=1;
        return status;
    }

    public void launchBrowserStackURL() {
        driver.get(readPropertiesFile("browserStackURL"));
    }

    public void signIn() throws InterruptedException {
        Thread.sleep(3000);
        driver.manage().window().maximize();
        bsSignIn.click();

        bsUserName.sendKeys(readPropertiesFile("bsUsername"));
        bsUserPassword.sendKeys(readPropertiesFile("bsPassword"));

        bsUserSubmit.click();

        driver.get("https://live.browserstack.com/");
    }

    public void liveSession() throws InterruptedException {
        Thread.sleep(3000);
        liveModule.click();

        assert(liveDashboard.isDisplayed()) :"Live dashboard is not launched!";
        logger.info("Live dashboard is launched successfully.");
        Thread.sleep(3000);


    }

    public void liveWindowsOS() {
        logger.info(" Live windows OS");
        List<WebElement> windowsOS=driver.findElements(By.xpath("//*[@class='accordion__content']//div[@role=\"listitem\"]"));
        int max=windowsOS.size()-1;
        int i= getRandomNum(max,1);
        windowsOS.get(i).click(); //selected random windows OS
        logger.info("Selected windows OS ** "+ windowsOS.get(i).getText());


    }
    int max,min;
    public int getRandomNum(int max, int min)
    {
       return  new Random().nextInt(max - min + 1) + 1;
    }

    public void liveWindowsBrowser() throws InterruptedException {
        Thread.sleep(3000);
        logger.info("Selecting random Chrome browser");
        List<WebElement> chromeBrowser=driver.findElements(By.xpath("//*[@data-test-browser='chrome']//div[@role='listitem']"));
        int i=getRandomNum(chromeBrowser.size()-1,1);

        logger.info("Selecting chrome browser version- " + chromeBrowser.get(i).getText());
        chromeBrowser.get(i).click();

        logger.info("launching browser....");
    }

    public int liveSessionTesting() throws InterruptedException {
        Thread.sleep(3000);
        try
        {
            if(liveSessionToolbar.isDisplayed())
            {

                logger.info("Live session is established, browser is launched");

               // Thread.sleep(9000);
                WebElement ele=driver.findElement(By.xpath("//*[@class='toolbar__head__icon-collapse']"));
                driver.findElement(By.xpath("//*[@class='toolbar__head__icon-collapse']")).click();
                driver.findElement(By.xpath("//*[@id='settings']")).click();
                Thread.sleep(10000);
                logger.info("Live session ");
                Actions builder = new Actions(driver);
                builder.moveByOffset(500, 40)
                        .click()
                        .pause(3000)
                        .sendKeys("BrowserStack" + Keys.ENTER)
                        .perform();
                Thread.sleep(3000);
                status=1;
            }
        }
        catch(Exception e)
        {

        }
        return status;
    }

    public void testStatus(int status) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        if (status==1) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test Passed\"}}");
        }
        else
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"Failed\", \"reason\": \"Test Failed\"}}");

    }
}