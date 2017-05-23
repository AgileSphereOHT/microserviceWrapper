package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorInvocationException;
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

    private String configFile; // = "delegators/delegators.properties";

    public Map<String, DelegatorRegistryEntry> registry;

    public DelegatorRegistry(String loaderName, String altConfigFile) {
        jarFileName = loaderName;
        configFile = altConfigFile;
        registry = new ConcurrentHashMap<String, DelegatorRegistryEntry>();
        this.registerDelegators();
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
        String rtn;
        Set<String> paramNames = paramMap.keySet();
        int paramCount = paramNames.size();
        String[] params = new String[paramCount];

        Iterator it = paramNames.iterator();
        int ix = 0;
        while (it.hasNext()) {
            String paramName = (String) it.next();
            String[] paramValues = paramMap.get(paramName); //request.getParameter(paramName);
            logger.info("Parameter " + ix + " name = " + paramName + " value = " + paramValues[0]);
            params[ix] = paramValues[0];
            ix++;
        }

        String expectedReturnType = getEntry(key).getReturnType();

        return getResponse(key, params, expectedReturnType);
    }

    private String getResponse(String key, String[] params, String returnType) {
        String returnString;
        Object invocationResponse = invokeDelegator(key, params);

        switch (returnType) {
            case "Array" :
                returnString = returnArray((String[])invocationResponse);
                break;
            case "List" :
                returnString = returnList((List<String>)invocationResponse);
                break;
            case "Map" :
                returnString = returnMap((Map<String, String>)invocationResponse);
                break;
            case "String" :
                returnString = (String) invocationResponse;
                break;
            default:
                String expectedErrorMessage = "Unable to handle invoked method return type for look up key " + key + " with intended return type = " + returnType;
                logger.error(expectedErrorMessage);
                throw new DelegatorInvocationException(expectedErrorMessage);
        }
        return returnString;
    }

    private Object invokeDelegator(String key, String... parameters) {
        DelegatorRegistryEntry entry = registry.get(key);
        logger.info("Registry Entry found = " + entry.getKey());
        return entry.getDelegator().invokeMethod(parameters);
    }

    private String returnArray(String[] returnedArray) {
        return Arrays.toString(returnedArray);
    }

    private String returnList(List<String> returnedList) {
        StringBuilder sb = new StringBuilder();
        for (String s : returnedList)
        {
            sb.append(s);
            sb.append(",").append(' ');;
        }
        return sb.toString();
    }

    private String returnMap(Map<String, String> returnedMap) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iter = returnedMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }

}
