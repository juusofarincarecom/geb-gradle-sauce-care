package pages


import geb.Page
import groovy.util.logging.Slf4j
import io.qameta.allure.Step
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.RemoteWebDriver
import pages.modules.UrgencyModule

import pages.modules.ZipCodeModule

@Slf4j
class BasePage extends Page {
    
    static at = { title == "Care.com: Find Child Care, Babysitters, Senior Care, Pet Care and Housekeeping" }
    
    static url = "/"
    
    static content = {
        applyToJobsButton { $(isMobile() ? "span" : "a", text: "Apply to jobs") }
        urgencyModule { module(UrgencyModule) }
        zipCode { module(ZipCodeModule) }
    }
    
    @Step("Click Apply to Jobs")
    def clickApplyToJobs() {
        applyToJobsButton.click()
    }
    
    Boolean isMobile() {
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities()
        // Get the platformName capability
        String platformName = capabilities.getCapability("platformName").toString()
        // Determine if the WebDriver instance is running on a mobile device
        if(platformName.equalsIgnoreCase("Android") || platformName.equalsIgnoreCase("iOS")) {
            System.out.println("The WebDriver instance is running on a mobile device")
            return true
        }
        else {
            System.out.println("The WebDriver instance is running on a desktop or tablet")
            return false
        }
    }
    
}
