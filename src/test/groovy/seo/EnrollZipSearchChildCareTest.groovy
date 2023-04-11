package seo


import groovy.util.logging.Slf4j
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import pages.BasePage
import pages.enroll.EnrollmentAppPage
import pages.enroll.ProviderProfileFormEnrollmentPage
import pages.enroll.SinglePageEnrollmentPage
import pages.seo.ChildCarePage

@Slf4j
@Epic("Epic")
@Feature("Feature")
@Story("Story")
class EnrollZipSearchChildCareTest extends Junit5BaseTest {
    
    @Test
    @Description("Go To ChildCare Page and Start Enrollment with Zip Code Search Submit")
    void EnrollZipSearchChildCare() {
        page = to(ChildCarePage)
        page.urgencyModule.waitForUrgencyModule()
        page.urgencyModule.clickCloseButton()
        page.urgencyModule.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        at(EnrollmentAppPage)
    }
    
    
}