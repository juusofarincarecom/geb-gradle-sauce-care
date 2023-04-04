package pages.error

import geb.Page

class NotAllowedErrorPage extends Page {
    
    static at = { driver.pageSource.contains("HTTP Status 405") }
    
}

