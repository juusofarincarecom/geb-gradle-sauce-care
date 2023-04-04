package pages.modules

import geb.Module
import io.qameta.allure.Step

class UrgencyModule extends Module {
    
    static content = {
        popup(wait: true, required: false) { $("div", role: "presentation", class: contains("MuiDialog-root")) }
        rightNow(wait: true, required: false) { $("p", text: "Right now") }
        closeButton(wait: true, required: false) { $("button", class: contains("MuiIconButton-sizeSmall")) }
    }
    
    @Step("Wait for pop up to show")
    def waitForPopUp() {
        waitFor(10) { popup.displayed }
    }
    
    @Step("Click Urgency Module Right Now Button")
    def clickRightNowButton() {
        rightNow.click()
    }
    
    @Step("Click Urgency Module Close Button")
    def clickCloseButton() {
        closeButton.click()
    }
    
    def closeButtonNotDisplayed() {
        waitFor(5) { !closeButton.displayed }
    }
}
