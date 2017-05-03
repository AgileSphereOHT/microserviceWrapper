package uk.co.agilesphere.wrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Delegator {

    private static Logger logger = LoggerFactory.getLogger(Delegator.class);

    private String libraryClassName;
    private String methodName;
    private Class libraryClass;
    private Object delegate;
    private Method invocableMethod;

    public Delegator(String libraryClassName, String methodName, Class[] parameters) {
        this.libraryClassName = libraryClassName;
        this.methodName = methodName;
        this.libraryClass = obtainClass(libraryClassName);
        this.delegate = constructDelegate(libraryClass);
        this.invocableMethod = getInvocableMethod(libraryClass, methodName, parameters);
    }

    public String invokeMethod(Object[] params) {   // TODO use varargs
        Object ret;
        try {
            ret = invocableMethod.invoke(delegate, params);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ixe) {
            String expectedErrorMessage = "Unable to invoke method " + methodName + " for class = " + libraryClassName;  //TODO show failing params
            logger.error(expectedErrorMessage);
            throw new DelegatorInvocationException(expectedErrorMessage, ixe);
        }
        return (String) ret;
    }

    private Class obtainClass(String libraryClassName) {
        Class libraryClass;
        try {
            libraryClass = Class.forName(libraryClassName);
        } catch (ClassNotFoundException cnfe) {
            final String expectedErrorMessage = "Unable to create class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, cnfe);
        }
        return libraryClass;
    }

    private Object constructDelegate(Class libraryClass) {
        Object delegate;
        try {
            Constructor<?> cons = libraryClass.getDeclaredConstructor();
            delegate = cons.newInstance();
        } catch (NoSuchMethodException nsme) {
            final String expectedErrorMessage = "Unable to find constructor for class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, nsme);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException ixe) {
            final String expectedErrorMessage = "Unable to construct object for class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, ixe);
        }
        return delegate;
    }

    private Method getInvocableMethod(Class libraryClass, String methodName, Class[] parameters) {
        Method foundMethod;
        try {
            foundMethod = libraryClass.getDeclaredMethod(methodName, parameters);
        } catch (NoSuchMethodException nsme) {
            String expectedErrorMessage = "Unable to find invocable method " + methodName + " with " + parameters.length + " parameters for class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, nsme);
        }
        return foundMethod;
    }

}


