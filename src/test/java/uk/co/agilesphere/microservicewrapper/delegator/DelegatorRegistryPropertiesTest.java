package uk.co.agilesphere.microservicewrapper.delegator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorRegistrationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class DelegatorRegistryPropertiesTest {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryPropertiesTest.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCreation() {
        DelegatorRegistryProperties props = new DelegatorRegistryProperties();
        assertNotNull(props);
    }

    //TODO revisit this for a failure due to internal InputStream error this doesn't really test anything :(
    //TODO Maybe take out mocking and replace with test props in test resources
    @Test
    public void testLoadPropertiesWithDelegatorRegistrationException() {
        final String expectedErrorMessage = "Unable to load delegator properties from file";
        expectedException.expect(DelegatorRegistrationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        DelegatorRegistryProperties mockedDelegatorRegistryProperties = mock(DelegatorRegistryProperties.class);
        doThrow(new DelegatorRegistrationException(expectedErrorMessage)).when(mockedDelegatorRegistryProperties).loadProperties();
        mockedDelegatorRegistryProperties.loadProperties();
    }

    @Test
    public void testLoadPropertiesTestForSingleProperty() throws IOException {
        DelegatorRegistryProperties delegatorRegistryProperties = org.mockito.Mockito.spy(new DelegatorRegistryProperties("./src/test/resources/delegators/testdelegators.properties"));
        Mockito.when(delegatorRegistryProperties.getInputStream()).thenReturn(new ByteArrayInputStream("myproperty=goodvalue".getBytes()));
        delegatorRegistryProperties.loadProperties();
        Properties theProperties = delegatorRegistryProperties.getProperties();
        assertEquals("Property found ", "goodvalue", theProperties.getProperty("myproperty"));
    }

    @Test
    public void testLoadPropertiesFromClasspathPropertyFile() throws IOException {
        DelegatorRegistryProperties delegatorRegistryProperties = new DelegatorRegistryProperties("./src/test/resources/delegators/testdelegators.properties");
        delegatorRegistryProperties.loadProperties();
        Properties theProperties = delegatorRegistryProperties.getProperties();
        assertEquals("Property 0 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respond,String", theProperties.getProperty("testkey0"));
        assertEquals("Property 1 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondOneParamAsString,String,param1", theProperties.getProperty("testkey1"));
        assertEquals("Property 2 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondTwoParamAsString,String,param1,param2", theProperties.getProperty("testkey2"));
        assertEquals("Property 3 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondThreeParamAsString,String,param1,param2,param3", theProperties.getProperty("testkey3"));
        assertEquals("Property 4 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondThreeParamAsArray,Array,param1,param2,param3", theProperties.getProperty("testkey4"));
        assertEquals("Property 5 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondThreeParamAsList,List,param1,param2,param3", theProperties.getProperty("testkey5"));
        assertEquals("Property 6 found", "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor,respondThreeParamAsMap,Map,param1,param2,param3", theProperties.getProperty("testkey6"));
    }
}


