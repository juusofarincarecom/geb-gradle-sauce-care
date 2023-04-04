package pages.modules

import geb.Module
import io.qameta.allure.Step

class VisitorHeaderModule extends Module {
    
    static content = {
        header { $("header", class: contains("visitor")) }
        childCare { header.$("p", text: "Child Care") }
        tutoring { header.$("p", text: "Tutoring") }
    }
    
    @Step("Click Child Care")
    def clickChildCare() {
        childCare.click()
    }
    
    @Step("Click Tutoring")
    def clickTutoring() {
        tutoring.click()
    }
}
