package seo


import groovy.util.logging.Slf4j
import io.qameta.allure.Description
import org.junit.jupiter.api.Test
import pages.BasePage
import pages.enroll.EnrollmentAppPage
import pages.enroll.ProviderProfileFormEnrollmentPage
import pages.enroll.SinglePageEnrollmentPage

@Slf4j
class ApplyToJobsTest extends Junit5BaseTest {
    
    @Test
    @Description("Apply to Jobs from VHP")
    void ApplyToJobs() {
        page = to(BasePage)
        page.clickApplyToJobs()
        atOneOfPages(SinglePageEnrollmentPage, ProviderProfileFormEnrollmentPage, EnrollmentAppPage)
    }
    
}