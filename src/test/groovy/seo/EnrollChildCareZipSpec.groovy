package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.ChildCarePage
import pages.EnrollmentAppPage
import spock.BaseSpec

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class EnrollChildCareZipSpec extends BaseSpec {
    
    @Description("Open ChildCare SEO Page and Start Enrollment with Zip Code")
    def "Enroll tutoring SEO Page with Zip Code"() {
        when:
        page = to(ChildCarePage)
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