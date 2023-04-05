package pages.enroll

import pages.BasePage

class EnrollmentAppPage extends BasePage {
    
    static at = { app }
    
    static url = "app/enrollment"
    
    static content = {
        app (wait: true) { $("div", class: contains("MuiBox-root"), state: "entered") }
        zipCode(wait: true) { $("#zipCode-label") }
    }
}
