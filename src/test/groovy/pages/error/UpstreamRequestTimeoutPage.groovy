package pages.error

import geb.Page

class UpstreamRequestTimeoutPage extends Page {
    
    static at = { error }
    
    static content = {
        error { $("pre", text: "upstream request timeout") }
    }
    
}
