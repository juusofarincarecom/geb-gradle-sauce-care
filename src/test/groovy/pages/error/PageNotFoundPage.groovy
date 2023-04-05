package pages.error

import geb.Page

class PageNotFoundPage extends Page {
    
    static at = { error }
    
    static content = {
        error { $("h2", text: "Page not found.") }
    }
    
}
