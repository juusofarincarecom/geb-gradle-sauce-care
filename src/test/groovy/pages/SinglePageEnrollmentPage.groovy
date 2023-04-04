package pages

class SinglePageEnrollmentPage extends BasePage {
    
    static at = { logo }
    
    static url = "enroll-care-seeker-p1042-q111085935.html"
    
    static content = {
        logo { $("a", 'aria-label': "Care.com logo") }
        form { $("form", id: "singlePageEnrollmentForm") }
    }
}
