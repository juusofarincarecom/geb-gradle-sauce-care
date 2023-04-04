package pages.error

import geb.Page

class AuthReqPage extends Page {
    
    static at = { forbidden }
    
    static content = {
        forbidden(wait: "quick", waitCondition: { it.displayed }) { $("h1", text: "403 Forbidden") }
    }
}

