package pages.error

import geb.Page

class AkamaiReferencePage extends Page {
    
    static at = { akamaiReference }
    
    static content = {
        akamaiReference(wait: "quick", waitCondition: { it.displayed }) { $("p", text: startsWith("Reference #")) }
    }
}

