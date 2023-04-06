package pages.error

import geb.Page

class ForbiddenErrorPage extends Page {
    
    static at = { forbidden }
    
    static content = {
        forbidden { $("h1", text: "403 Forbidden") }
    }
}

