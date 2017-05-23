package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorRegistrationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorRegistry {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistry.class);

    String jarFileName;

    ClassLoader jarFileLoader;

    private DelegatorRegistryProperties properties;

    private String configFile;

    public Map<String, DelegatorRegistryEntry> registry;

    private DelegatorResponder delegatorResponder;

    public DelegatorResponder getDelegatorResponder() {
        return delegatorResponder;
    }

    public DelegatorRegistry(String loaderName, String altConfigFile) {
        jarFileName = loaderName;
        configFile = altConfigFile;
        registry = new ConcurrentHashMap<String, DelegatorRegistryEntry>();
        this.registerDelegators();
        delegatorResponder = new DelegatorResponder(this);
    }

    public DelegatorRegistry() {
        registry = new ConcurrentHashMap<String, DelegatorRegistryEntry>();
        this.registerDelegators();
    }

    public void registerDelegators() {
        Properties props = getConfiguredProperties();
        try {
            File jarFile = new File(jarFileName);
            if (jarFile.exists()) {
                logger.info("Found jar file " + jarFileName);
            } else {
                final String expectedErrorMessage = "Missing jar file " + jarFileName;
                logger.error(expectedErrorMessage);
                throw new DelegatorRegistrationException(expectedErrorMessage);
            }
            URL jarFileUrl = jarFile.toURI().toURL();
            jarFileLoader = new URLClassLoader(new URL[]{jarFileUrl}, Thread.currentThread().getContextClassLoader());
        } catch (IOException ioe) {
            final String expectedErrorMessage = "Unable to load delegator properties from file";
            logger.error(expectedErrorMessage);
            throw new DelegatorRegistrationException(expectedErrorMessage, ioe);
        }

        Enumeration<?> e = props.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = props.getProperty(key);
            logger.debug("Registering delegator for key=" + key + " with props=" + value);
            DelegatorRegistryEntry entry = DelegatorRegistryEntry.generateFromPropertyDefinition(key, value, jarFileLoader);
            registry.put(key, entry);
        }
    }

    private Properties getConfiguredProperties() {
        logger.info("In getConfiguredProperties");
        //TODO think we are loading this every use - cache?
        properties = new DelegatorRegistryProperties(configFile);
        properties.loadProperties();
        return properties.getProperties();
    }

    public DelegatorRegistryEntry getEntry(String key) {
        return registry.get(key);
    }

    public String getResult(String key, Map<String, String[]> paramMap) {
        return delegatorResponder.getResult(key,paramMap);
    }
}