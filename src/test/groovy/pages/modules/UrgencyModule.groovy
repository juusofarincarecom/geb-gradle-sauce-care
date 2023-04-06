package pages.modules

import geb.Module
import io.qameta.allure.Step

class UrgencyModule extends Module {
    
    static content = {
        urgencyModule(wait: "verySlow") { $("div", role: "presentation", class: contains("MuiDialog-root")) }
        rightNow(wait: true) { $("p", text: "Right now") }
        closeButton(wait: true, required: false) { $("button", class: contains("MuiIconButton-sizeSmall")) }
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
        browser.waitFor() { !closeButton.displayed }
    }
}
