package uk.co.agilesphere.microservicewrapper.delegator;


import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorConfigurationException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uk.co.agilesphere.microservicewrapper.delegator.TestConstants.*;

public class DelegatorRegistryTests {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryTests.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static String jarPath;
    private static String configPath;

    @BeforeClass
    public static void setup() {
        String jarFileLoc = "src/test/resources/testjar/dummy-service-0.0.1-SNAPSHOT.jar";
        File jarFile = new File(jarFileLoc);
        jarPath = jarFile.getAbsolutePath();

        String configDirLoc = "src/test/resources/delegators";
        File configDir = new File(configDirLoc);
        configPath = configDir.getAbsolutePath() + "/";
        logger.info("DelegatorRegistryTests - Test Jar Path = " + jarPath);
        logger.info("DelegatorRegistryTests - Config Dir Path = " + configPath);

    }

    @Test
    public void testLoadDelegatorPropertiesInvalidClass() {
        final String expectedErrorMessage = "Unable to create class = " + LIBRARY_CLASS_DOES_NOT_EXIST_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        String configFile = "unknownclass.properties";
        DelegatorRegistry registry = new DelegatorRegistry(jarPath, configPath + configFile);
        registry.registerDelegators();
    }

    @Test
    public void testLoadDelegatorPropertiesNoInvocableServiceMethod() {
        final String expectedErrorMessage =
                "Unable to find invocable method respondX with 0 parameters on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        String configFile = "unknownmethod.properties";
        DelegatorRegistry registry = new DelegatorRegistry(jarPath, configPath + configFile);
        registry.registerDelegators();
    }

    @Test
    public void testLoadDelegatorPropertiesMatchedServiceMethod() {
        String configFile = "testdelegators.properties";
        String key = "testkey1";
        DelegatorRegistry registry = new DelegatorRegistry(jarPath, configPath + configFile);
        registry.registerDelegators();
        DelegatorRegistryEntry entry = registry.getEntry(key);
        assertNotNull("No matched service method found for key=" + key, entry);
        assertThat(entry.getKey(), is("testkey1"));
        assertThat(entry.getClassName(), Matchers.is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(entry.getMethodName(), Matchers.is(METHOD_WITH_ONE_PARAM_NAME));
        assertThat(entry.getReturnType(), Matchers.is(STRING_RETURN_TYPE));
        assertThat(entry.getParameterNames(), arrayContaining("param1"));
        assertThat(entry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void testRegisteredMethodInvocationWithOneParamSuccess() {
        String configFile = "testdelegators.properties";
        String key = "testkey1";
        DelegatorRegistry registry = new DelegatorRegistry(jarPath, configPath + configFile);
        registry.registerDelegators();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("param1", ONE_PARAMETER);
        String result = (String) registry.getResult(key, params);
        assertEquals("Method invocation response", "Param1 = param1", result);
    }
}
