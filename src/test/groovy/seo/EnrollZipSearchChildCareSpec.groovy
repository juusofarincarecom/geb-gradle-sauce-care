package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.enroll.EnrollmentAppPage
import pages.seo.ChildCarePage
import spock.BaseSpec

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class EnrollZipSearchChildCareSpec extends BaseSpec {
    
    @Description("Go To ChildCare Page and Start Enrollment with Zip Code Search Submit")
    def "Enroll with Zip Code Search from ChildCare Page"() {
        when:
        page = to(ChildCarePage)
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