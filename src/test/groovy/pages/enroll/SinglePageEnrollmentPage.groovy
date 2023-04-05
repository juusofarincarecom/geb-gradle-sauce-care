package pages.enroll

import pages.BasePage

class SinglePageEnrollmentPage extends BasePage {
    
    static at = { form }
    
    static url = "enroll-care-seeker-p1042-q111085935.html"
    
    static content = {
        form { $("form", id: "singlePageEnrollmentForm") }
    }
}
