package pages.error

import pages.BasePage

class UpstreamRequestTimeoutPage extends BasePage {
    
    static at = { upstreamRequestTimeout }
    
    static content = {
        upstreamRequestTimeout { $("pre", text: "upstream request timeout") }
    }
    
}
