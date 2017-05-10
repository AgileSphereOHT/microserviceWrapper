package uk.co.agilesphere.microservicewrapper.delegator;


import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorConfigurationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uk.co.agilesphere.microservicewrapper.delegator.TestConstants.*;

public class DelegatorRegistryTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testLoadDelegatorPropertiesInvalidClass() {
        final String expectedErrorMessage = "Unable to create class = " + LIBRARY_CLASS_DOES_NOT_EXIST_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        String configFile = "delegators/unknownclass.properties";
        DelegatorRegistry registry = new DelegatorRegistry(configFile);
        registry.registerDelegators();
    }

    @Test
    public void testLoadDelegatorPropertiesNoInvocableServiceMethod() {
        final String expectedErrorMessage =
                "Unable to find invocable method respondX with 0 parameters on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        String configFile = "delegators/unknownmethod.properties";
        DelegatorRegistry registry = new DelegatorRegistry(configFile);
        registry.registerDelegators();
    }

    @Test
    public void testLoadDelegatorPropertiesMatchedServiceMethod() {
        String configFile = "delegators/testdelegators.properties";
        String key = "testkey1";
        DelegatorRegistry registry = new DelegatorRegistry(configFile);
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
        String configFile = "delegators/testdelegators.properties";
        String key = "testkey1";
        DelegatorRegistry registry = new DelegatorRegistry(configFile);
        registry.registerDelegators();
        DelegatorRegistryEntry entry = registry.getEntry(key);
        Delegator delegator = entry.getDelegator();
        String[] oneParam = ONE_PARAMETER;
        String result = (String) delegator.invokeMethod(oneParam);
        assertEquals("Method invocation response", "Param1 = param1", result);
    }
}
