package uk.co.agilesphere.wrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.wrapper.delegator.exception.DelegatorRegistrationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DelegatorRegistryProperties {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryProperties.class);

    private String propsFileName = "delegators/delegators.properties";
    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public DelegatorRegistryProperties() {
    }

    //TODO Could configure the properties as an external file
    protected InputStream getInputStream() {
        return DelegatorRegistryProperties.class.getResourceAsStream(propsFileName);
    }

    protected void loadProperties() {
        try {
            InputStream in = this.getInputStream();
            properties.load(in);
        } catch (IOException ioe) {
            final String expectedErrorMessage = "Unable to load delegator properties from file";
            logger.error(expectedErrorMessage);
            throw new DelegatorRegistrationException(expectedErrorMessage, ioe);
        }
    }
}
