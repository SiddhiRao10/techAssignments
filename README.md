BrowserStack- Automate module Assignments
========== BrowserStack Demo Site ==========
mvn test -P BrowserStackDemoSite

========= Amazon Product Search ==========
mvn test -P AmazonProductSearchParallelTest

========== BrowserStack Inception ==========
 mvn test -P LiveInception

========= to run localtesting script ========
mvn -Dexec.mainClass="bsLocalTesting.localTesting" -Dexec.classpathScope=test test-compile exec:java