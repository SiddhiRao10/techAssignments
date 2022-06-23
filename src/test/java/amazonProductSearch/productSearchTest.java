package amazonProductSearch;

import bsDemoCommon.BasePO;
import bsDemoCommon.BaseTest;
import org.testng.annotations.Test;

public class productSearchTest extends BaseTest {
    public productSearchTest() {
    }

    @Test
    public void searchProduct() throws InterruptedException {
        BasePO po = new BasePO(this.driver);

        logger.info("*** Launching amazon URL***");
        po.launchAmazonURL();

        logger.info("*** searching product on amazon***");
        po.amazonSearchProduct();

        logger.info("*** to verify search page filters");
        po.searchedPageFilter();

        logger.info("searched result page");
        po.resultPage();
    }
}
