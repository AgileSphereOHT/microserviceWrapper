package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

public class DelegatorRegistryEntry {

    public static final int PROPERTY_FIELDS_BEFORE_PARAMETERS = 3;
    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistryEntry.class);

    private String key;
    private String className;
    private String methodName;
    private String returnType;
    private String[] parameterNames;

    private Delegator delegator;

    public DelegatorRegistryEntry(String key, String className, String methodName, String returnType, String[] parameterNames) {
        this.setKey(key);
        this.setClassName(className);
        this.setMethodName(methodName);
        this.setReturnType(returnType);
        this.setParameterNames(parameterNames);
    }

    public DelegatorRegistryEntry(String key, String className, String methodName, String returnType, String[] parameterNames, Delegator delegator) {
        this(key, className, methodName, returnType, parameterNames);
        this.delegator = delegator;
    }

    //Property definition key, class name, method name, return type, zero to many parameters
    public static DelegatorRegistryEntry generateFromPropertyDefinition(String propertyKey, String propertyDefinition) {
        DelegatorRegistryEntry entry = null;
        StringTokenizer tokens = new StringTokenizer(propertyDefinition,",");
        int parameterCount = tokens.countTokens() - PROPERTY_FIELDS_BEFORE_PARAMETERS;
        //while (tokens.hasMoreTokens()) {
            String className = tokens.nextToken();
            String methodName = tokens.nextToken();
            String returnType = tokens.nextToken();
            String[] parameters = new String[parameterCount];
            for (int ix=0; ix<parameterCount;ix++) {
                parameters[ix] = tokens.nextToken();
            }
            Delegator delegator = new Delegator(className,methodName,parameters);
            entry = new DelegatorRegistryEntry(propertyKey,className,methodName,returnType,parameters,delegator);
        //}
        return entry;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames;
    }

    public Delegator getDelegator() {
        return delegator;
    }

    public void setDelegator(Delegator delegator) {
        this.delegator = delegator;
    }
}
