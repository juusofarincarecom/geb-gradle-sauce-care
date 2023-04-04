package pages


import pages.modules.UrgencyModule
import pages.modules.VisitorHeaderModule
import pages.modules.ZipCodeModule

class ChildCarePage extends BasePage {
    
    static at = { title == "Find Child Care Near Me | Child Care Providers Nearby" }
    
    static url = "childcare"
    
    static content = {
        popup { module(UrgencyModule) }
        header { module(VisitorHeaderModule) }
        zipCode { module(ZipCodeModule) }
    }
    
}
