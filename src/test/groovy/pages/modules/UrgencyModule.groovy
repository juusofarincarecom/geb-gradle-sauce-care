package pages.modules

import groovy.util.logging.Slf4j
import io.qameta.allure.Step

@Slf4j
class UrgencyModule extends BaseModule {
    
    static content = {
        urgencyModule(wait: "slow") { $("div", role: "presentation", class: contains("MuiDialog-root")) }
        rightNow { urgencyModule.$("p", text: "Right now") }
        closeButton(required: false) { $("button", class: contains("MuiIconButton-sizeSmall")) }
    }
    
    @Step("Wait for Urgency Module to show")
    def waitForUrgencyModule() {
        waitFor("slow") { urgencyModule.displayed }
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
        waitFor("slow") { closeButton.isEmpty() }
    }
}
