package seo


import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.seo.ChildCarePage
import pages.enroll.EnrollmentAppPage
import pages.seo.TutoringPage
import spock.BaseSpec
import spock.lang.Stepwise
import spock.lang.Unroll

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
        page.popup.waitForPopUp()
        page.popup.clickCloseButton()
        page.popup.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        then:
        at(EnrollmentAppPage)
    }
    
}