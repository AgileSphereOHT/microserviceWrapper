package uk.co.agilesphere.wrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DelegatorRegistry {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistry.class);

    private DelegatorRegistryProperties properties;

    private Map<String, DelegatorRegistryEntry> registry = new HashMap<String, DelegatorRegistryEntry>();

    public DelegatorRegistry() {
    }

    public DelegatorRegistry(DelegatorRegistryProperties properties) {
        this.properties = properties;
    }

    public void registerDelegators() {
            properties = new DelegatorRegistryProperties();
            properties.loadProperties();
            Properties props = properties.getProperties();
            Enumeration<?> e = props.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = props.getProperty(key);
                DelegatorRegistryEntry entry = DelegatorRegistryEntry.generateFromPropertyDefinition(key,value);
                registry.put(key,entry);
            }

    }

/*    public void registerDelegators() {
        try {
            properties = new DelegatorRegistryProperties();
            properties.loadProperties();
        } catch (IOException ioe) {
            final String expectedErrorMessage = "Unable to register delegators";
            logger.error(expectedErrorMessage);
            throw new DelegatorRegistrationException(expectedErrorMessage, ioe);
        }
    }*/
    //Read from property file
    //Load configuration into Configuration
    //   key: URL    libraryClassName, methodName, methodReturnType, parameters[ paramName, paramType]

    //Create Delegator for each configured delegator which can be mapped to a service class / method / parameter combo
    //Allow look up and retrieval by url key

    //

}
