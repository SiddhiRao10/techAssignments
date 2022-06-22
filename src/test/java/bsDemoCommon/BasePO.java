package bsDemoCommon;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
}

