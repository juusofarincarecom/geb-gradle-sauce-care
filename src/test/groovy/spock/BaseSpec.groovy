package spock

import geb.Page
import geb.spock.GebReportingSpec
import groovy.util.logging.Slf4j
import io.qameta.allure.Allure
import io.qameta.allure.Step
import org.openqa.selenium.Capabilities
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.remote.RemoteWebDriver
import spock.lang.Shared

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import static java.nio.charset.StandardCharsets.US_ASCII
import static org.apache.commons.lang3.StringUtils.isNotBlank

@Slf4j
class BaseSpec extends GebReportingSpec {
    
    @Shared
    Boolean isSauceLabs = isNotBlank(USER_ENV_VAR)
    
    /**
     * Instance variables for the Sauce Job.*/
    private String sessionId
    private String sauceUrl
    private Capabilities capabilities
    public static String USER_ENV_VAR = System.getenv("GEB_SAUCE_LABS_USER")
    private static String ACCESS_KEY_ENV_VAR = System.getenv("GEB_SAUCE_LABS_ACCESS_PASSWORD")
    private static final String KEY = String.format("%s:%s", USER_ENV_VAR, ACCESS_KEY_ENV_VAR)
    private static final String SAUCE_TESTS_URL = "https://app.saucelabs.com/tests"
    
    @Shared
    Page page
    
    @Step("Setup")
    def setup() {
        if(isSauceLabs) {
            setupSauceLabsTestData()
        }
    }
    
    @Step("Setup SauceLabs Test Data")
    private void setupSauceLabsTestData() {
        if(isSauceLabs) {
            this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString()
            this.sauceUrl = getSauceLink(sessionId)
            log.info("SauceOnDemandSessionID=${sessionId} job-name=${this.class.name}")
            log.info("Test Job Link: " + sauceUrl)
            log.info("[[ATTACHMENT|${sauceUrl}]]")
            Allure.addAttachment("SauceLabs Job Video", "text/html", sauceUrl)
            ((JavascriptExecutor) driver).executeScript("sauce:job-name=${this.class.name}")
            ((JavascriptExecutor) driver).executeScript("sauce:job-tags=tag1,tag2,tag3")
            ((JavascriptExecutor) driver).executeScript("sauce:job-build=" + System.getProperty("JENKINS_BUILD_NUMBER", "1"))
        }
    }
    
    @Step("Get Sauce Live view url")
    private String getSauceLink(String sauceJobId) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec sks = new SecretKeySpec(KEY.getBytes(US_ASCII), "HmacMD5")
        Mac mac = Mac.getInstance("HmacMD5")
        mac.init(sks)
        byte[] result = mac.doFinal(sauceJobId.getBytes(US_ASCII))
        StringBuilder hash = new StringBuilder()
        for(byte b : result) {
            String hex = Integer.toHexString(0xFF & b)
            if(hex.length() == 1) {
                hash.append('0')
            }
            hash.append(hex)
        }
        String digest = hash.toString()
        return String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest)
    }
    
    @Step("To the page: [{page}]") <T extends Page> T to(Class<T> page) {
        browser.to page
    }
    
    @Step("At the page: [{page}]") <T extends Page> T at(Class<T> page) {
        browser.at page
    }
    
    @Step("At one of pages: [{pages}]") <T extends Page> T atOneOfPages(Class<T>... pages) {
        browser.page(pages) as T
    }
    
    @Step("Go to URL: [{url}]")
    void go(String url) {
        browser.go url
    }
    
}