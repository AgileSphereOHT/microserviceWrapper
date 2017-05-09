package uk.co.agilesphere.microservicewrapper.delegator;

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
                logger.debug("Registering delegator for key="+key+ " with props="+value);
                DelegatorRegistryEntry entry = DelegatorRegistryEntry.generateFromPropertyDefinition(key,value);
                registry.put(key,entry);
            }

    }

    public void invokeDelegator(String key, String... parameters) {
        DelegatorRegistryEntry entry = registry.get(key);
        entry.getDelegator().invokeMethod(parameters);
    }

}
