package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.enroll.EnrollmentAppPage
import pages.seo.TutoringPage
import spock.BaseSpec

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class EnrollZipSearchTutoringSpec extends BaseSpec {
    
    @Description("Go To Tutoring Page and Start Enrollment with Zip Code Search Submit")
    def "Enroll with Zip Code Search from Tutoring Page"() {
        when:
        page = to(TutoringPage)
        and:
        page.urgencyModule.waitForUrgencyModule()
        page.urgencyModule.clickCloseButton()
        page.urgencyModule.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        then:
        at(EnrollmentAppPage)
    }
    
}