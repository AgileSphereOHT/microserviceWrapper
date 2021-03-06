package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorConfigurationException;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorInvocationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Delegator {

    private static Logger logger = LoggerFactory.getLogger(Delegator.class);

    private String libraryClassName;
    private String methodName;
    private Class libraryClass;
    private Object delegate;
    private Method invocableMethod;

    public Delegator(ClassLoader jarFileLoader, String libraryClassName, String methodName, String... parameterNames) {
        this.libraryClassName = libraryClassName;
        this.methodName = methodName;
        this.libraryClass = obtainLoadedClass(jarFileLoader, libraryClassName);

        this.delegate = constructDelegate(libraryClass);
        //TODO parameters here are the mames of String parameters - need to allow name and Type?
        this.invocableMethod = getInvocableMethod(libraryClass, methodName, parameterNames);
    }

    public Object invokeMethod(Object... params) {
        Object ret;
        String paramsAsString = Arrays.toString(params);
        try {
            //TODO provide pre-check of parameter number and type (all String at the mo) vs params
            logger.info("Invoking method " + methodName + " on class = " + libraryClassName + " with params "+ paramsAsString);
            ret = invocableMethod.invoke(delegate, params);
        } catch (IllegalAccessException | IllegalArgumentException iae) {
            String expectedErrorMessage = "Unable to invoke method " + methodName + " on class = " + libraryClassName + " with params "+ paramsAsString;
            logger.error(expectedErrorMessage);
            throw new DelegatorInvocationException(expectedErrorMessage, iae);
        } catch (InvocationTargetException ite) {
            String expectedErrorMessage = "Exception thrown when invoking method " + methodName + " on class = " + libraryClassName + " with params "+ paramsAsString + " : Message: " + ite.getCause().getMessage();
            logger.error(expectedErrorMessage);
            throw new DelegatorInvocationException(expectedErrorMessage, ite);
        }
        return ret;
    }

    private Class obtainLoadedClass(ClassLoader jarFileLoader, String className) {
        Class clazz;
        try {
            clazz = jarFileLoader.loadClass(className);
        } catch (ClassNotFoundException cnfe) {
            final String expectedErrorMessage = "Unable to create class = " + className;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, cnfe);
        }
        return clazz;
    }

    private Class obtainClass(String className) {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
            final String expectedErrorMessage = "Unable to create class = " + className;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, cnfe);
        }
        return clazz;
    }

    private Object constructDelegate(Class libraryClass) {
        Object delegate;
        try {
            Constructor<?> cons = libraryClass.getDeclaredConstructor();    // TODO this is with no arg constructor - contrains service classes OR allow
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

    private Method getInvocableMethod(Class libraryClass, String methodName, String... parameterNames) {
        Method foundMethod;

        Class[] parameterClasses = new Class[parameterNames.length];
        for (int ix = 0; ix < parameterNames.length; ix++) {
            parameterClasses[ix] = obtainClass("java.lang.String");
        }

        try {
            foundMethod = libraryClass.getDeclaredMethod(methodName, parameterClasses);
        } catch (NoSuchMethodException nsme) {
            String expectedErrorMessage = "Unable to find invocable method " + methodName + " with " + parameterNames.length + " parameters on class = " + libraryClassName;
            logger.error(expectedErrorMessage);
            throw new DelegatorConfigurationException(expectedErrorMessage, nsme);
        }
        return foundMethod;
    }

}


