package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DelegatorRegistry {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistry.class);

    private DelegatorRegistryProperties properties;

    private String configFile = "delegators/delegators.properties";

    private Map<String, DelegatorRegistryEntry> registry = new HashMap<String, DelegatorRegistryEntry>();

    public DelegatorRegistry(String altConfigFile) {
        configFile = altConfigFile;
    }

    public void registerDelegators() {
        Properties props = getConfiguredProperties();

        Enumeration<?> e = props.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = props.getProperty(key);
            logger.debug("Registering delegator for key=" + key + " with props=" + value);
            DelegatorRegistryEntry entry = DelegatorRegistryEntry.generateFromPropertyDefinition(key, value);
            registry.put(key, entry);
        }
    }

    private Properties getConfiguredProperties() {
        properties = new DelegatorRegistryProperties(configFile);
        properties.loadProperties();
        return properties.getProperties();
    }

    public DelegatorRegistryEntry getEntry(String key) {
        return registry.get(key);
    }

    public void invokeDelegator(String key, String... parameters) {
        DelegatorRegistryEntry entry = registry.get(key);
        entry.getDelegator().invokeMethod(parameters);
    }

}
