package bsLiveInception;

import bsDemoCommon.BasePO;
import bsDemoCommon.BaseTest;
import bsDemoCommon.BrowserStackTestNGTest;
import org.testng.annotations.Test;

public class liveInceptionTest  extends BrowserStackTestNGTest {
    public liveInceptionTest() {
    }

    @Test
    public void liveSession() throws InterruptedException {
        BasePO po = new BasePO(this.driver);

        logger.info("*** Launching BrowserStack URL***");
        po.launchBrowserStackURL();

        logger.info("*** BrowserStack SignIn***");
        po.signIn();

        logger.info("*** BrowserStack Live Session***");
        po.liveSession();

        logger.info("*** Select random windows OS***");
        po.liveWindowsOS();

        logger.info("*** Select windows browser***");
        po.liveWindowsBrowser();

        logger.info("*** Live Session testing on launched browser***");
        po.liveSessionTesting();

    }
}
