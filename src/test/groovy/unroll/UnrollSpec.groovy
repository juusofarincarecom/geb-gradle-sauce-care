package unroll


import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.ChildCarePage
import pages.EnrollmentAppPage
import pages.TutoringPage
import spock.BaseSpec
import spock.lang.Unroll

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class UnrollSpec extends BaseSpec {
    
    @Unroll("#startPage")
    @Description("Open #startPage SEO Page and Start Enrollment with Zip Code")
    def "#startPage"() {
        when:
        browser.clearCookies()
        page = to(startPage)
        and:
        page.popup.waitForUrgencyModule()
        page.popup.clickCloseButton()
        page.popup.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        then:
        at(EnrollmentAppPage)
        where:
        startPage << [TutoringPage, ChildCarePage]
    }
    
}