package pages.error

import geb.Page

class AkamaiReferencePage extends Page {
    
    static at = { akamaiReference }
    
    static content = {
        akamaiReference { $("p", text: startsWith("Reference #")) }
    }
}

