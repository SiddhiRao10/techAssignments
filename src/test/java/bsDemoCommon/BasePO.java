package bsDemoCommon;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BasePO {
    public static final Logger logger = Logger.getLogger(String.valueOf(BasePO.class));
    protected WebDriver driver;
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

    public void orderStatus() {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        if(orderStatus.isDisplayed()) {
            if(orderStatus.getText().equals("Your Order has been successfully placed."))
                logger.info("Your Order has been successfully placed.");
        }
        else
            logger.info("order has not been placed successfully");
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
        Select drpCountry = new Select(driver.findElement(By.name("s")));
        drpCountry.selectByVisibleText("Price: High to Low");
        Thread.sleep(3000);
    }

    public void resultPage() throws InterruptedException {

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
    }
}


