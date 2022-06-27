package bsLiveInception;

import bsDemoCommon.BasePO;
import bsDemoCommon.BaseTest;
import bsDemoCommon.BrowserStackTestNGTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class liveInceptionTest  extends BrowserStackTestNGTest {
    public liveInceptionTest() {
    }

    @Test
    @Parameters(value={"browser"})
    public void liveSession(String environment) throws InterruptedException {
        BasePO po = new BasePO(this.driver);

        logger.info(environment.toString());
        logger.info("*** Launching BrowserStack URL***");
        po.launchBrowserStackURL();

        logger.info("*** BrowserStack SignIn***");
        po.signIn();

        logger.info("*** BrowserStack Live Session***");
        po.liveSession();

        logger.info("*** Select random windows OS***");
        po.liveWindowsOS();

        logger.info("*** Select windows browser***");
        po.liveWindowsBrowser(environment.toString());

        logger.info("*** Live Session testing on launched browser***");
        int status=po.liveSessionTesting();

        logger.info("test status");
        po.testStatus(status);

    }
}
