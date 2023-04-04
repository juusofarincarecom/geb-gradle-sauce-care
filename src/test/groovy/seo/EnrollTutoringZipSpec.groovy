package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.EnrollmentAppPage
import pages.TutoringPage
import spock.BaseSpec

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class EnrollTutoringZipSpec extends BaseSpec {
    
    @Description("Open Tutoring SEO Page and Start Enrollment with Zip Code")
    def "Enroll tutoring SEO Page with Zip Code"() {
        when:
        page = to(TutoringPage)
        and:
        page.popup.waitForPopUp()
        page.popup.clickCloseButton()
        page.popup.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        then:
        at(EnrollmentAppPage)
    }
    
}