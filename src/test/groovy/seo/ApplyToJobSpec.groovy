package seo


import groovy.util.logging.Slf4j
import io.qameta.allure.*
import pages.BasePage
import pages.enroll.EnrollmentAppPage
import pages.enroll.ProviderProfileFormEnrollmentPage
import pages.enroll.SinglePageEnrollmentPage
import spock.BaseSpec

@Story("Story")
@Epic("Epic")
@Feature("Feature")
@Severity(SeverityLevel.BLOCKER)
@Slf4j
class ApplyToJobSpec extends BaseSpec {

    @Description("Go Visitor Home Page and click Apply to Job")
    def "Go Visitor Home Page and click Apply to Job"() {
        when:
        page = to(BasePage)
        and:
        page.clickApplyToJobs()
        then:
        atOneOfPages(SinglePageEnrollmentPage, ProviderProfileFormEnrollmentPage, EnrollmentAppPage)
    }
    
}