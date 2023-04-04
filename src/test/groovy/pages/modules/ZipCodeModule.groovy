package pages.modules

import geb.Module
import geb.module.TextInput
import io.qameta.allure.Step
import org.openqa.selenium.Keys

class ZipCodeModule extends Module {
    
    static content = {
        zipCode(wait: true) { $("input", id: "zipCode") }
    }
    
    @Step("Add Zip Code")
    def addZipCode() {
        def input = zipCode.module(TextInput)
        input.text = "20001"
    }
    
    @Step("Submit Zip Code")
    def submitZipCode() {
        zipCode.singleElement().sendKeys(Keys.ENTER)
    }
}
