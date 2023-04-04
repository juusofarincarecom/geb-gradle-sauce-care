package pages

class EnrollmentAppPage extends BasePage {
    
    static at = { app }
    
    static url = "app/enrollment"
    
    static content = {
        app { $("div", state: "entered") }
        zipCode(wait: true) { $("#zipCode-label") }
    }
}
