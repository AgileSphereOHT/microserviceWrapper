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
    public static final String METHOD_WITH_ONE_PARAM_NAME = "respondOneParamAsString";
    public static final String METHOD_WITH_TWO_PARAM_NAME = "respondTwoParamAsString";
    public static final String METHOD_WITH_THREE_PARAM_NAME = "respondThreeParamAsString";

    public static final String METHOD_DOES_NOT_EXIST_NAME = "methoddoesnotexist";

    static final Class[] zeroParameters = new Class[]{};
    static final Class[] oneParameter = new Class[]{String.class};
    static final Class[] twoParameters = new Class[]{String.class, String.class};
    static final Class[] threeParameters = new Class[]{String.class, String.class, String.class};
    //static final Class[] tooManyParameters = new Class[]{String.class, String.class, String.class, String.class};


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testLibraryClassInstantationWithZeroParamMethodOK() {
        Class[] parameters = new Class[]{};
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
        assertNotNull(delegator);
    }

    @Test
    public void testLibraryClassInstantationWithOneParamMethodOK() {
        Class[] parameters = new Class[]{};
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_ONE_PARAM_NAME, oneParameter);
        assertNotNull(delegator);
    }

    @Test
    public void testLibraryClassInstantationWithTwoParamMethodOK() {
        Class[] parameters = new Class[]{};
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_TWO_PARAM_NAME, twoParameters);
        assertNotNull(delegator);
    }

    @Test
    public void testLibraryClassInstantationWithThreeParamMethodOK() {
        Class[] parameters = new Class[]{};
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_THREE_PARAM_NAME, threeParameters);
        assertNotNull(delegator);
    }

    @Test
    public void testInvalidClassRequested() {
        final String expectedErrorMessage = "Unable to create class = " + LIBRARY_CLASS_DOES_NOT_EXIST_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_DOES_NOT_EXIST_NAME, METHOD_DOES_NOT_EXIST_NAME, zeroParameters);
    }

    @Test
    public void testInvalidNoParamMethodRequested() {
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_DOES_NOT_EXIST_NAME + " with 0 parameters for class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_DOES_NOT_EXIST_NAME, zeroParameters);
    }

    @Test
    public void testInvalidTooManyParamsMethodRequested() {
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_WITH_ONE_PARAM_NAME + " with 2 parameters for class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_ONE_PARAM_NAME, twoParameters);
    }

    @Test
    public void testInvalidConstructorRequested() {
        final String expectedErrorMessage = "Unable to find constructor for class = " + LIBRARY_CLASS_WITH_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        //Doesn't have a no arg constructor but we aren't providing the arg it needs
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
    }

    //TODO this doesn't trigger the exception and there are three cases to catch anyway... all hard to replicate... may revisit
    /*
    @Test
    public void testFailedObjectConstruction() {
        final String expectedErrorMessage = "Unable to construct object for class = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage("Unable to construct object for class = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
    }
    */

    @Test
    public void testMethodInvocationException() {
        final String expectedErrorMessage = "Unable to invoke method " + METHOD_WITH_NO_PARAMS_NAME + " for class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorInvocationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
        String[] illegalParams = {"param1", "param2"};
        delegator.invokeMethod(illegalParams);
    }

    @Test
    public void testMethodInvocationSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
        String[] noParams = {};
        String result = delegator.invokeMethod(noParams);
        assertEquals("Method invocation", "RETURNED", result);
    }
}
