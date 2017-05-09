package uk.co.agilesphere.microservicewrapper.delegator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorRegistrationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

public class DelegatorRegistryPropertiesTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void creationTest() {
        DelegatorRegistryProperties props = new DelegatorRegistryProperties();
        assertNotNull(props);
    }

    //TODO revisit this for a failure due to internal InputStream error this doesn't really test anything :(
    //TODO Maybe take out mocking and replace with test props in test resources
    @Test
    public void loadPropertiesWithDelegatorRegistrationException() {
        final String expectedErrorMessage = "Unable to load delegator properties from file";
        expectedException.expect(DelegatorRegistrationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        DelegatorRegistryProperties mockedDelegatorRegistryProperties = mock(DelegatorRegistryProperties.class);
        doThrow(new DelegatorRegistrationException(expectedErrorMessage)).when(mockedDelegatorRegistryProperties).loadProperties();
        mockedDelegatorRegistryProperties.loadProperties();
    }

    @Test
    public void loadPropertiesTestForSingleProperty() throws IOException {
        DelegatorRegistryProperties delegatorRegistryProperties = org.mockito.Mockito.spy(new DelegatorRegistryProperties());
        Mockito.when(delegatorRegistryProperties.getInputStream()).thenReturn(new ByteArrayInputStream("myproperty=goodvalue".getBytes()));
        delegatorRegistryProperties.loadProperties();
        Properties theProperties = delegatorRegistryProperties.getProperties();
        assertEquals("Property found ", "goodvalue", theProperties.getProperty("myproperty"));
    }


}


