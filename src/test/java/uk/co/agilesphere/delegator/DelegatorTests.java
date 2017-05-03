package uk.co.agilesphere.delegator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.agilesphere.wrapper.delegator.Delegator;
import uk.co.agilesphere.wrapper.delegator.DelegatorConfigurationException;
import uk.co.agilesphere.wrapper.delegator.DelegatorInvocationException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public static final String METHOD_WITH_THREE_PARAM_AS_ARRAY_NAME = "respondThreeParamAsArray";
    public static final String METHOD_WITH_THREE_PARAM_AS_LIST_NAME = "respondThreeParamAsList";
    public static final String METHOD_WITH_THREE_PARAM_AS_MAP_NAME = "respondThreeParamAsMap";
    public static final String METHOD_THROWING_SERVICE_EXCEPTION_NAME = "respondWithException";
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
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_DOES_NOT_EXIST_NAME + " with 0 parameters on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_DOES_NOT_EXIST_NAME, zeroParameters);
    }

    @Test
    public void testInvalidTooManyParamsMethodRequested() {
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_WITH_ONE_PARAM_NAME + " with 2 parameters on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_ONE_PARAM_NAME, twoParameters);
    }

    @Test
    public void testInvalidConstructorRequested() {
        final String expectedErrorMessage = "Unable to find constructor on class = " + LIBRARY_CLASS_WITH_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        //Doesn't have a no arg constructor but we aren't providing the arg it needs
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
    }

    //TODO this doesn't trigger the exception and there are three cases to catch anyway... all hard to replicate... may revisit
    /*
    @Test
    public void testFailedObjectConstruction() {
        final String expectedErrorMessage = "Unable to construct object on class = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage("Unable to construct object on class = "+ LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
        Delegator delegator = new Delegator(LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME);
    }
    */

    @Test
    public void testMethodInvocationException() {
        final String expectedErrorMessage = "Unable to invoke method " + METHOD_WITH_NO_PARAMS_NAME + " on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorInvocationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
        String[] illegalParams = {"param1", "param2"};
        delegator.invokeMethod(illegalParams);
    }

    @Test
    public void testMethodInvocationServiceException() {
        final String expectedErrorMessage = "Exception thrown when invoking method " + METHOD_THROWING_SERVICE_EXCEPTION_NAME + " on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME + " with message: A library class exception";
        expectedException.expect(DelegatorInvocationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_THROWING_SERVICE_EXCEPTION_NAME, zeroParameters);
        String[] noParams = {};
        delegator.invokeMethod(noParams);
    }

    @Test
    public void testMethodInvocationWithNoParamsSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_NO_PARAMS_NAME, zeroParameters);
        String[] noParams = {};
        String result = (String) delegator.invokeMethod(noParams);
        assertEquals("Method invocation response", "RETURNED", result);
    }

    @Test
    public void testMethodInvocationWithOneParamSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_ONE_PARAM_NAME, oneParameter);
        String[] oneParam = {"param1"};
        String result = (String) delegator.invokeMethod(oneParam);
        assertEquals("Method invocation response", "Param1 = param1", result);
    }

    @Test
    public void testMethodInvocationWithTwoParamsAndStringResponseSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_TWO_PARAM_NAME, twoParameters);
        String[] twoParams = {"param1", "param2"};
        String result = (String) delegator.invokeMethod(twoParams);
        assertEquals("Method invocation response", "Params = param1,param2", result);
    }

    @Test
    public void testMethodInvocationWithThreeParamsAndStringResponseSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_THREE_PARAM_NAME, threeParameters);
        String[] threeParams = {"param1", "param2", "param3"};
        String result = (String) delegator.invokeMethod(threeParams);
        assertEquals("Method invocation response", "Params = param1,param2,param3", result);
    }

    @Test
    public void testMethodInvocationWithThreeParamsAndArrayResponseSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_THREE_PARAM_AS_ARRAY_NAME, threeParameters);
        String[] threeParams = {"param1", "param2", "param3"};
        String[] result = (String[]) delegator.invokeMethod(threeParams);
        assertArrayEquals(threeParams, result);
    }

    @Test
    public void testMethodInvocationWithThreeParamsAndListResponseSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_THREE_PARAM_AS_LIST_NAME, threeParameters);
        String[] threeParams = {"param1", "param2", "param3"};
        List<String> returnList = new ArrayList<String>(Arrays.asList(threeParams));
        List<String> result = (List<String>) delegator.invokeMethod(threeParams);
        assertThat(result, is(returnList));
    }

    @Test
    public void testMethodInvocationWithThreeParamsAndMapResponseSuccess() {
        Delegator delegator = new Delegator(LIBRARY_CLASS_NO_ARG_CONS_NAME, METHOD_WITH_THREE_PARAM_AS_MAP_NAME, threeParameters);
        String[] threeParams = {"param1", "param2", "param3"};
        final Stream<AbstractMap.SimpleEntry<String, String>> entryStream = Stream.of(
                new AbstractMap.SimpleEntry<>("param1", "param1"),
                new AbstractMap.SimpleEntry<>("param2", "param2"),
                new AbstractMap.SimpleEntry<>("param3", "param3"));
        Map<String, String> returnMap = Collections.unmodifiableMap(entryStream.collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
        Map<String, String> result = (Map<String, String>) delegator.invokeMethod(threeParams);
        assertThat(result, is(returnMap));
    }
}
