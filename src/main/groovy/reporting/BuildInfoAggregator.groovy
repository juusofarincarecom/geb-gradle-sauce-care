package reporting

import com.google.common.collect.ImmutableMap
import groovy.util.logging.Slf4j
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Slf4j
class BuildInfoAggregator {
    
    private static final String ENVIRONMENT_FILE_NAME = "environment.xml"
    private static final String ENVIRONMENT_FILE_PATH = "build/reports/allure-results/" + ENVIRONMENT_FILE_NAME
    
    static void main(String[] args) {
        BuildInfoAggregator creator = new BuildInfoAggregator()
        creator.updateAllureEnvironmentXml()
    }
    
    static void updateAllureEnvironmentXml() {
        ImmutableMap<String, String> environmentValuesSet = ImmutableMap
            .of("Build", System.getProperty("JENKINS_BUILD_NUMBER", "1"),
                "Test Environment", System.getProperty("CARE_TEST_ENVIRONMENT", "stg"),
                "Browser", System.getProperty("browserName", "Chrome"),
                "Platform", System.getProperty("platformName", "Mac"),
                "browserVersion", System.getProperty("browserVersion", "latest"))
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance()
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder()
            Document doc = docBuilder.newDocument()
            
            Element environment = doc.createElement("environment")
            doc.appendChild(environment)
            
            for(String key : environmentValuesSet.keySet()) {
                Element parameter = doc.createElement("parameter")
                Element keyElement = doc.createElement("key")
                Element valueElement = doc.createElement("value")
                
                keyElement.appendChild(doc.createTextNode(key))
                valueElement.appendChild(doc.createTextNode(environmentValuesSet.get(key)))
                
                parameter.appendChild(keyElement)
                parameter.appendChild(valueElement)
                environment.appendChild(parameter)
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance()
            Transformer transformer = transformerFactory.newTransformer()
            DOMSource source = new DOMSource(doc)
            
            File allureResultsDir = new File("build/reports/allure-results/")
            if(!allureResultsDir.exists()) {
                allureResultsDir.mkdirs()
            }
            
            StreamResult result = new StreamResult(new File(ENVIRONMENT_FILE_PATH))
            transformer.transform(source, result)
            
        }
        catch(ParserConfigurationException | TransformerException e) {
            log.error("Unable to parse/transform {}. Exception: {}", ENVIRONMENT_FILE_PATH, e)
        }
    }
}