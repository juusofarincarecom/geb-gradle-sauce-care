/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/#configuration
*/


import geb.driver.SauceLabsDriverFactory
import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import geb.report.ScreenshotReporter
import io.qameta.allure.Allure
import org.apache.commons.io.FilenameUtils
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import pages.error.PageNotFoundPage
import pages.error.UpstreamRequestTimeoutPage

import java.nio.file.Files

cacheDriver = false
cacheDriverPerThread = false
atCheckWaiting = false
autoClearCookies = false
baseNavigatorWaiting = true
reportOnTestFailureOnly = true
unexpectedPages = [UpstreamRequestTimeoutPage, PageNotFoundPage]
requirePageAtCheckers = true


// 7.2.6. Waiting
waiting {
    timeout = 10 // wait 5 seconds for an element to appear
    retryInterval = 0.1
    includeCauseInMessage = true
    presets {
        verySlow {
            timeout = 20
            retryInterval = 2
        }
        slow {
            timeout = 15
            retryInterval = 0.5
        }
        quick {
            timeout = 5
        }
    }
}

// Local Drivers
environments {
	
	// run via “./gradlew chromeTest”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		driver = { new ChromeDriver() }
	}

	// run via “./gradlew chromeHeadlessTest”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chromeHeadless {
		driver = {
			ChromeOptions o = new ChromeOptions()
			o.addArguments('headless')
			new ChromeDriver(o)
		}
	}
    
    // run via “./gradlew firefoxTest”
    // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
    firefox {
        atCheckWaiting = 1
        
        driver = { new FirefoxDriver() }
    }
    
}

// Cloud Drivers
// run via “./gradlew allSauceLabsTests”
// See: build.gradle sauceLabs task and https://opensource.saucelabs.com/sauce_bindings/
def sauceLabsBrowser = System.getProperty("geb.saucelabs.browser")
if(sauceLabsBrowser) {
    driver = {
        def username = System.getenv("GEB_SAUCE_LABS_USER")
        assert username
        def accessKey = System.getenv("GEB_SAUCE_LABS_ACCESS_PASSWORD")
        assert accessKey
        new SauceLabsDriverFactory().create(sauceLabsBrowser, username, accessKey)
    }
}

// To run the tests with local and cloud drivers just run “./gradlew test”

// Care.com BaseUrl
baseUrl = getCareBaseUrl(System.getProperty("CARE_TEST_ENVIRONMENT", "stg"))

static String getCareBaseUrl(String env = "stg") {
    switch(env) {
        case "dev":
            return "https://www.dev.carezen.net/"
        case "prod":
            return "https://www.care.com/"
        default:
            return "https://www.stg.carezen.net/"
    }
}

//https://plugins.jenkins.io/junit-attachments/
reportingListener = new ReportingListener() {
    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
        reportFiles.each {
            System.out.println "[[ATTACHMENT|$it.absolutePath]]"
            String extension = FilenameUtils.getExtension(it.name)
            InputStream is = Files.newInputStream(it.toPath())
            String type = reporter instanceof ScreenshotReporter ? "image/png" : "text/html"
            Allure.addAttachment it.name, type, is as InputStream, extension
        }
    }
}