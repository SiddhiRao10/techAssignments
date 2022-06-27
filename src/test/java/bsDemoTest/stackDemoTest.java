package bsDemoTest;

import bsDemoCommon.BasePO;
import bsDemoCommon.BaseTest;

import bsDemoCommon.BrowserStackTestNGTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class stackDemoTest extends BrowserStackTestNGTest {
    public stackDemoTest() {
    }

    @Test
    public void searchProduct() throws InterruptedException, URISyntaxException, IOException {
       BasePO po = new BasePO(this.driver);
        logger.info("*** Launching BS demo URL***");
        po.launchStackURL();

        logger.info("*** login *** ");
        po.login();

        logger.info("*** Search product and add to cart****");
        po.searchProductAddCart();

        logger.info("*** checkout ***");
        po.checkout();

        logger.info("*** shipping details screen***");
        po.shippingAddressDetails();

        logger.info("*** order status***");
        int status= po.orderStatus();

        logger.info("test status");
        po.testStatus(status);

    }
}
