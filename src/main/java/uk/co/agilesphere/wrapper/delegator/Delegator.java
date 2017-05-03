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

    public Object invokeMethod(Object[] params) {   // TODO use varargs
        Object ret;
        try {
            ret = invocableMethod.invoke(delegate, params);
        } catch (IllegalAccessException | IllegalArgumentException iae) {
            String expectedErrorMessage = "Unable to invoke method " + methodName + " on class = " + libraryClassName;  //TODO show failing params
            logger.error(expectedErrorMessage);
            throw new DelegatorInvocationException(expectedErrorMessage, iae);
        } catch (InvocationTargetException ite) {
            String expectedErrorMessage = "Exception thrown when invoking method " + methodName + " on class = " + libraryClassName + " with message: " + ite.getCause().getMessage();  //TODO show failing params
            logger.error(expectedErrorMessage);
            throw new DelegatorInvocationException(expectedErrorMessage, ite);
        }
        return ret;
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
            final String expectedErrorMessage = "Unable to find constructor on class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, nsme);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException ixe) {
            final String expectedErrorMessage = "Unable to construct object on class = " + libraryClassName;
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
            String expectedErrorMessage = "Unable to find invocable method " + methodName + " with " + parameters.length + " parameters on class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, nsme);
        }
        return foundMethod;
    }

}


