package seo

import geb.Page
import groovy.util.logging.Slf4j
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pages.BasePage
import pages.enroll.EnrollmentAppPage
import pages.enroll.ProviderProfileFormEnrollmentPage
import pages.enroll.SinglePageEnrollmentPage
import pages.seo.ChildCarePage
import pages.seo.TutoringPage

@Slf4j
@Epic("Epic")
@Feature("Feature")
@Story("Story")
@Severity(SeverityLevel.BLOCKER)
class ParametrizedExampleTest extends Junit5BaseTest {
    
//    @ParameterizedTest(name = "Verify title of {0} page")
//    @ValueSource(classes = [TutoringPage, ChildCarePage])
//    void ParametrizedExample(Page inputPage) {
//        to(inputPage);
//        page.urgencyModule.waitForUrgencyModule();
//        page.urgencyModule.clickCloseButton();
//        page.urgencyModule.closeButtonNotDisplayed();
//        page.zipCode.addZipCode();
//        page.zipCode.submitZipCode();
//        at(EnrollmentAppPage.class);
//    }
        
        @ParameterizedTest(name = "Verify title of {0} page")
        @ValueSource(classes = [TutoringPage, ChildCarePage])
        void ParametrizedExample(Class<? extends Page> pageClass) {
            Page inputPage = pageClass.newInstance()
            page = to(inputPage as Class<BasePage>)
            page.urgencyModule.waitForUrgencyModule();
            page.urgencyModule.clickCloseButton();
            page.urgencyModule.closeButtonNotDisplayed();
            page.zipCode.addZipCode();
            page.zipCode.submitZipCode();
            at(EnrollmentAppPage.class);
        }
        
}