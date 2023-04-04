package pages.error

import pages.BasePage

class PageNotFoundPage extends BasePage {
    
    static at = { error }
    
    static content = {
        error { $("h2", text: "Page not found.") }
    }
    
}
