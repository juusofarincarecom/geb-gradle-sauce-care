/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/#configuration
*/


import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import geb.report.ScreenshotReporter
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.qameta.allure.Allure
import org.apache.commons.io.FilenameUtils
import org.openqa.selenium.MutableCapabilities
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import pages.error.*

import java.nio.file.Files

import static org.openqa.selenium.Platform.WIN11

cacheDriver = true
cacheDriverPerThread = true
atCheckWaiting = true
autoClearCookies = false
autoClearWebStorage = false
baseNavigatorWaiting = true
reportOnTestFailureOnly = true
unexpectedPages = [UpstreamRequestTimeoutPage, PageNotFoundPage, AkamaiReferencePage, ForbiddenErrorPage, NotAllowedErrorPage]
requirePageAtCheckers = true
quitDriverOnBrowserReset = false

// 7.2.6. Waiting
waiting {
    timeout = 10 // wait 5 seconds for an element to appear
    retryInterval = 0.1
    includeCauseInMessage = true
    presets {
        verySlow {
            timeout = 30
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

static def isSauceLabs(String user = System.getenv("SAUCE_USERNAME"), String key = System.getenv("SAUCE_ACCESS_KEY")) {
    assert user
    assert key
}

def url = new URL("https://${System.getenv("SAUCE_USERNAME")}:${System.getenv("SAUCE_ACCESS_KEY")}@ondemand.us-west-1.saucelabs.com:443/wd/hub")

environments {
    // run via “./gradlew chromeTest”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome { driver = { new ChromeDriver() } }
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
    firefox { driver = { new FirefoxDriver() } }
    
    //run via “./gradlew {driver}
    //saucelabs drivers: ["edge_win", "firefox_mac", "chrome_mac", "safari_ios", "chrome_android"]
    edge_win {
        driver = {
            isSauceLabs()
            EdgeOptions edgeOptions = new EdgeOptions()
            edgeOptions.setPlatformName(WIN11.toString())
            edgeOptions.setBrowserVersion("latest")
            new RemoteWebDriver(url, edgeOptions)
        }
    }
    firefox_mac {
        driver = {
            isSauceLabs()
            FirefoxOptions firefoxOptions = new FirefoxOptions()
            firefoxOptions.setPlatformName("macOS 13")
            firefoxOptions.setBrowserVersion("latest")
            new RemoteWebDriver(url, firefoxOptions)
        }
    }
    chrome_mac {
        driver = {
            isSauceLabs()
            ChromeOptions chromeOptions = new ChromeOptions()
            chromeOptions.setPlatformName("macOS 13")
            chromeOptions.setBrowserVersion("latest")
            new RemoteWebDriver(url, chromeOptions)
        }
    }
    safari_ios {
        driver = {
            isSauceLabs()
            MutableCapabilities ios_caps = new MutableCapabilities()
            ios_caps.setCapability("platformName", "iOS")
            ios_caps.setCapability("browserName", "Safari")
            ios_caps.setCapability("appium:deviceName", "iPhone Fast Simulator")
            ios_caps.setCapability("appium:platformVersion", "previous_major")
            ios_caps.setCapability("appium:automationName", "XCUITest")
            MutableCapabilities ios_opts = new MutableCapabilities()
            ios_opts.setCapability("appiumVersion", "2.0.0-beta56")
            ios_opts.setCapability("build", System.getenv("JENKINS_BUILD_NUMBER"))
            ios_caps.setCapability("sauce:options", ios_opts)
            new IOSDriver(url, ios_caps)
        }
    }
    chrome_android {
        driver = {
            isSauceLabs()
            MutableCapabilities andr_caps = new MutableCapabilities()
            andr_caps.setCapability("platformName", "Android")
            andr_caps.setCapability("browserName", "Chrome")
            andr_caps.setCapability("appium:deviceName", "Android GoogleAPI Emulator")
            andr_caps.setCapability("appium:platformVersion", "12.0")
            andr_caps.setCapability("appium:automationName", "UiAutomator2")
            MutableCapabilities andr_opts = new MutableCapabilities()
            andr_opts.setCapability("appiumVersion", "2.0.0-beta56")
            andr_opts.setCapability("build", System.getenv("JENKINS_BUILD_NUMBER"))
            andr_caps.setCapability("sauce:options", andr_opts)
            new AndroidDriver(url, andr_caps)
        }
    }
}

baseUrl = getCareBaseUrl(System.getProperty("TEST_ENVIRONMENT", "stg"))

static def getCareBaseUrl(String env = "stg") {
    switch(env) {
        case "dev":
            return "https://www.dev.carezen.net/"
        case "prod":
            return "https://www.care.com/"
        default:
            return "https://www.stg.carezen.net/"
    }
}

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