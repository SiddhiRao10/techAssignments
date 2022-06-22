package bsDemoTest;

import bsDemoCommon.BasePO;
import bsDemoCommon.BaseTest;

import org.testng.annotations.Test;

public class stackDemoTest extends BaseTest {
    public stackDemoTest() {
    }

    @Test
    public void searchProduct() throws InterruptedException {
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
        po.orderStatus();
    }
}
