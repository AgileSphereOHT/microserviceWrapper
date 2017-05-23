package uk.co.agilesphere.microservicewrapper.delegator;


import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DelegatorResponderTest {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryTests.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static String jarPath;
    private static String configPath;
    private static DelegatorRegistry registry;
    private static DelegatorResponder responder;
    private static Map<String, String[]> oneInputParams = new HashMap<String, String[]>();
    private static Map<String, String[]> threeInputParams = new HashMap<String, String[]>();


    @BeforeClass
    public static void setup() {
        String jarFileLoc = "src/test/resources/testjar/dummy-service-0.0.1-SNAPSHOT.jar";
        File jarFile = new File(jarFileLoc);
        jarPath = jarFile.getAbsolutePath();

        String configFileLoc = "src/test/resources/delegators/testdelegators.properties";
        File configFile = new File(configFileLoc);
        configPath = configFile.getAbsolutePath();
        logger.info("DelegatorResponderTest - Test Jar Path = " + jarPath);
        logger.info("DelegatorResponderTest - Config File Path = " + configPath);

        oneInputParams = new HashMap<String, String[]>();
        threeInputParams = new HashMap<String, String[]>();
        String[] param1 = {"House"};
        String[] param2 = {"Mouse"};
        String[] param3 = {"Cat"};
        oneInputParams.put("param1", param1);
        threeInputParams.put("param1", param1);
        threeInputParams.put("param2", param2);
        threeInputParams.put("param3", param3);

        DelegatorRegistry registry = new DelegatorRegistry(jarPath, configPath);
        responder = registry.getDelegatorResponder();

    }

    @Test
    public void testReturnString() {
        String response = responder.getResult("testkey1", oneInputParams);
        assertEquals("Response", "Param1 = House", response);
    }

    @Test
    public void testReturnArray() {
        String response = responder.getResult("testkey4", threeInputParams);
        assertEquals("Response", "[Cat, House, Mouse]", response);
    }

    @Test
    public void testReturnList() {
        String response = responder.getResult("testkey5", threeInputParams);
        System.out.println("\n\nPOP\n\n" + response);
        assertEquals("Response", "Cat, House, Mouse, ", response);
    }

    @Test
    public void testReturnMap() {
        String response = responder.getResult("testkey6", threeInputParams);
        System.out.println("\n\nPOP\n\n" + response);
        assertEquals("Response", "param3=\"Mouse\", param1=\"Cat\", param2=\"House\"", response);

    }

}
