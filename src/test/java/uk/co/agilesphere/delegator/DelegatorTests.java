package uk.co.agilesphere.delegator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.agilesphere.wrapper.delegator.Delegator;
import uk.co.agilesphere.wrapper.delegator.DelegatorConfigurationException;
import uk.co.agilesphere.wrapper.delegator.DelegatorInvocationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
TODO Springrunner ??
*/
public class DelegatorTests {

    public static final String LIBRARY_CLASS_NO_ARG_CONS_NAME = "uk.co.agilesphere.delegator.LibraryClassNoArgConstructor";
    public static final String LIBRARY_CLASS_WITH_ARG_CONS_NAME = "uk.co.agilesphere.delegator.LibraryClassWithArgConstructor";
    public static final String LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME = "uk.co.agilesphere.delegator.LibraryClassWithPrivateConstructor";
    public static final String LIBRARY_CLASS_DOES_NOT_EXIST_NAME = "uk.co.agilesphere.delegator.LibraryClassDoesNotExist";
    public static final String METHOD_WITH_NO_PARAMS_NAME = "respond";
    public static final String METHOD_DOES_NOT_EXIST_NAME = "methoddoesnotexist";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testLibraryClassInstantationOK() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME);
        assertNotNull(delegator);
    }

    @Test
    public void testInvalidClassRequested() {
        final String expectedErrorMessage = "Unable to create class with name = " + LIBRARY_CLASS_DOES_NOT_EXIST_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_DOES_NOT_EXIST_NAME, METHOD_DOES_NOT_EXIST_NAME);
    }

    @Test
    public void testInvalidMethodRequested() {
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_DOES_NOT_EXIST_NAME + " for class with name = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_DOES_NOT_EXIST_NAME);
    }

    @Test
    public void testInvalidConstructorRequested() {
        final String expectedErrorMessage = "Unable to find constructor for class with name = " + LIBRARY_CLASS_WITH_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        //Doesn't have a no arg constructor but we aren't providing the arg it needs
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME);
    }

    //TODO this doesn't trigger the exception and there are three cases to catch anyway... all hard to replicate... may revisit
    /*
    @Test
    public void testFailedObjectConstruction() {
        final String expectedErrorMessage = "Unable to construct object for class with name = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage("Unable to construct object for class with name = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
    }
    */

    @Test
    public void testMethodInvocationException() {
        final String expectedErrorMessage = "Unable to invoke method " + METHOD_WITH_NO_PARAMS_NAME + " for class with name = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorInvocationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME);
        String[] illegalParams = {"param1", "param2"};
        delegator.invokeMethod(illegalParams);
    }

    @Test
    public void testMethodInvocationSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME);
        String[] noParams = {};
        String result = delegator.invokeMethod(noParams);
        assertEquals("Method invocation", "RETURNED", result);
    }
}
