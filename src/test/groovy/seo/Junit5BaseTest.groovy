package seo

import geb.Browser
import geb.Page
import geb.junit5.GebReportingTest
import geb.test.GebTestManager
import geb.test.GebTestManagerBuilder
import groovy.util.logging.Slf4j
import io.qameta.allure.Allure
import io.qameta.allure.Step
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.Capabilities
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.remote.RemoteWebDriver

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import static java.nio.charset.StandardCharsets.US_ASCII

@Slf4j
class Junit5BaseTest extends GebReportingTest {
    
    private final static GebTestManager TEST_MANAGER = new GebTestManagerBuilder()
        .withBrowserCreator { new Browser() }
        .withReportingEnabled(true)
        .build()
    
    @Override
    static GebTestManager getTestManager() {
        TEST_MANAGER
    }
    
    Boolean isSauceLabs = USER_ENV_VAR?.trim() ?: false
    
    Page page
    
    /**
     * Instance variables for the Sauce Job.*/
    private String sessionId
    private String sauceUrl
    private Capabilities capabilities
    private static String sauceUserEnvVar = "SAUCE_USERNAME"
    private static String sauceKeyEnvVar = "SAUCE_ACCESS_KEY"
    private static String USER_ENV_VAR = System.getenv(sauceUserEnvVar)
    private static String ACCESS_KEY_ENV_VAR = System.getenv(sauceKeyEnvVar)
    private static final String KEY = String.format("%s:%s", USER_ENV_VAR, ACCESS_KEY_ENV_VAR)
    private static final String SAUCE_TESTS_URL = "https://app.saucelabs.com/tests"
    
    @BeforeAll
    static void init(){
        log.info("BeforeAll init() method called")
    }
    
    @BeforeEach
    void initEach(){
        log.info("BeforeEach initEach() method called")
        if(isSauceLabs) { setupSauceLabsTestData() }
    }
    
    @Step("Setup SauceLabs Test Data")
    private void setupSauceLabsTestData() {
        if(isSauceLabs) {
            this.sessionId = (((RemoteWebDriver) browser.driver).getSessionId()).toString()
            this.sauceUrl = getSauceLink(sessionId)
            String testName = this.class.name
            log.info("SauceOnDemandSessionID=${sessionId} job-name=${testName}")
            log.info("Test Job Link: " + sauceUrl)
            log.info("[[ATTACHMENT|${sauceUrl}]]")
            Allure.addAttachment("SauceLabs Job Video", "text/html", sauceUrl)
            ((JavascriptExecutor) browser.driver).executeScript(String.format("sauce:job-name=%s", testName))
            ((JavascriptExecutor) browser.driver).executeScript("sauce:job-tags=" +  System.getenv("BUILD_TAG")?: "Tag")
            ((JavascriptExecutor) browser.driver).executeScript("sauce:job-build=" + System.getenv("JENKINS_BUILD_NUMBER") ?: "1")
        }
    }
    
    @Step("Get Sauce Live view url")
    private static String getSauceLink(String sauceJobId) throws NoSuchAlgorithmException, InvalidKeyException {
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