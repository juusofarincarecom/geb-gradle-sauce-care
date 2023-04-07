package pages.modules

import groovy.util.logging.Slf4j
import io.qameta.allure.Step

@Slf4j
class UrgencyModule extends BaseModule {
    
    static content = {
        urgencyModule(wait: "verySlow") { $("div", role: "presentation", class: contains("MuiDialog-root")) }
        rightNow { urgencyModule.$("p", text: "Right now") }
        closeButton(required: false) { urgencyModule.$("button", class: contains("MuiIconButton-sizeSmall")) }
    }
    
    @Step("Wait for Urgency Module to show")
    def waitForUrgencyModule() {
        browser.waitFor("verySlow") { urgencyModule.displayed }
    }
    
    @Step("Click Urgency Module 'Right Now' Button")
    def clickRightNowButton() {
        rightNow.click()
    }
    
    @Step("Click Urgency Module 'Close' Button")
    def clickCloseButton() {
        closeButton.click()
    }
    
    @Step("Wait For Urgency Module to not be displayed")
    def closeButtonNotDisplayed() {
        sleep(2000)
        //log.info("closeButton.displayed: " + closeButton.displayed.toString())
        log.info("closeButton.isDisplayed(): " + closeButton.isDisplayed())
        log.info("closeButton.isEmpty(): " + closeButton.isEmpty())
        log.info("closeButton.isFocused(): " + closeButton.isFocused())
        log.info("closeButton.value(): " + closeButton.value())
        log.info("closeButton.allElements(): " + closeButton.allElements())
        log.info("closeButton.tag(): " + closeButton.tag())
        
        assert !closeButton.displayed
    }
}
