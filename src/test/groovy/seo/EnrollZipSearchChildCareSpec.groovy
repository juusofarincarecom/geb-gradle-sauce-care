package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.enroll.EnrollmentAppPage
import pages.seo.ChildCarePage
import pages.seo.TutoringPage
import spock.BaseSpec
import spock.lang.Stepwise

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
        page.popup.waitForPopUp()
        page.popup.clickCloseButton()
        page.popup.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        then:
        at(EnrollmentAppPage)
    }
    
}