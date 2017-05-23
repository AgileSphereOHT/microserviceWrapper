package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorRegistrationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DelegatorRegistryProperties {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryProperties.class);

    private String configFile = "delegators/delegators.properties";

    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public DelegatorRegistryProperties(String altConfigFile) {
        logger.info("In DelegatorRegistryProperties " + altConfigFile);
        this.configFile = altConfigFile;
    }

    public DelegatorRegistryProperties() {
    }

    protected InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(configFile);
    }

    public void loadProperties() {
        try (InputStream in = this.getInputStream()) {
            properties.load(in);
        } catch (IOException ioe) {
            final String expectedErrorMessage = "Unable to load delegator properties from file " + this.configFile;
            logger.error(expectedErrorMessage);
            throw new DelegatorRegistrationException(expectedErrorMessage, ioe);
        }
    }
}
