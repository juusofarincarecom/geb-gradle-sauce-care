package pages.enroll

import pages.BasePage

class ProviderProfileFormEnrollmentPage extends BasePage {
    
    static at = { form }
    
    static url = "swf"
    
    static content = {
        form(wait: true) { $("form", id: "providerProfileForm") }
    }
}
