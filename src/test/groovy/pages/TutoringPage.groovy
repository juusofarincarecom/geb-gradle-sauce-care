package pages

import pages.modules.UrgencyModule
import pages.modules.VisitorHeaderModule
import pages.modules.ZipCodeModule

class TutoringPage extends BasePage {
    
    static at = { title == "Find Tutors, Online Tutors Near Me - Affordable Pricing - Care.com" }
    
    static url = "tutoring"
    
    static content = {
        popup { module(UrgencyModule) }
        header { module(VisitorHeaderModule) }
        zipCode { module(ZipCodeModule) }
    }
}
