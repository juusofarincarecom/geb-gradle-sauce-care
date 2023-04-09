package seo

import geb.Page
import io.qameta.allure.*
import org.apache.tools.ant.util.XMLFragment
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import pages.BasePage
import pages.enroll.EnrollmentAppPage
import pages.seo.ChildCarePage
import pages.seo.TutoringPage

@Epic("Epic")
@Feature("Feature")
class IterationTest extends Junit5BaseTest {
    
    @Story("Story")
    @Severity(SeverityLevel.BLOCKER)
    @ParameterizedTest(name = "Verify title of {0} page")
    @ValueSource(classes = [TutoringPage, ChildCarePage])
    def testStartPage(BasePage page) {
        browser.clearCookies();
        to(page);
        page.urgencyModule.waitForUrgencyModule();
        page.urgencyModule.clickCloseButton();
        page.urgencyModule.closeButtonNotDisplayed();
        page.zipCode.addZipCode();
        page.zipCode.submitZipCode();
        at(EnrollmentAppPage.class);
    }
    
    
}