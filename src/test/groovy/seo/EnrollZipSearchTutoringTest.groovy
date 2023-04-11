package seo

import groovy.util.logging.Slf4j
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.Ignore
import org.junit.jupiter.api.Test
import pages.enroll.EnrollmentAppPage
import pages.seo.ChildCarePage
import pages.seo.TutoringPage

@Slf4j
@Epic("Epic")
@Feature("Feature")
@Story("Story")
@Ignore
class EnrollZipSearchTutoringTest extends Junit5BaseTest {
    
    @Test
    @Description("Go To Tutoring Page and Start Enrollment with Zip Code Search Submit")
    void EnrollZipSearchTutoring() {
        page = to(TutoringPage)
        page.urgencyModule.waitForUrgencyModule()
        page.urgencyModule.clickCloseButton()
        page.urgencyModule.closeButtonNotDisplayed()
        page.zipCode.addZipCode()
        page.zipCode.submitZipCode()
        at(EnrollmentAppPage)
    }
    
    
}