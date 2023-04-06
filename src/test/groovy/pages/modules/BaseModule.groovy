package pages.modules

import geb.Module
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.RemoteWebDriver

class BaseModule extends Module {
    
    Boolean isMobile() {
        Capabilities capabilities = ((RemoteWebDriver) browser.driver).getCapabilities()
        // Get the platformName capability
        String platformName = capabilities.getCapability("platformName").toString()
        // Determine if the WebDriver instance is running on a mobile device
        if(platformName.equalsIgnoreCase("Android") || platformName.equalsIgnoreCase("iOS")) {
            System.out.println("The WebDriver instance is running on a mobile device")
            return true
        }
        else {
            System.out.println("The WebDriver instance is running on a desktop or tablet")
            return false
        }
    }
    
}
