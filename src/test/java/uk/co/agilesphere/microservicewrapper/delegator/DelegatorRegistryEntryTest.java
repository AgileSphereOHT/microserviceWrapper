package uk.co.agilesphere.microservicewrapper.delegator;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorConfigurationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.co.agilesphere.microservicewrapper.delegator.TestConstants.*;

public class DelegatorRegistryEntryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    //TODO other combos producing exceptions tested under DelegatorTests... may revisit to increase coverage beyong these two here?
    @Test
    public void testParseDelegatorDefinitionUnmatchedOnClassNameException() {
        final String expectedErrorMessage = "Unable to create class = " + LIBRARY_CLASS_DOES_NOT_EXIST_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_DOES_NOT_EXIST_NAME + "," + METHOD_WITH_NO_PARAMS_NAME + "," + UNDEFINED_RETURN_TYPE;
        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
    }

    @Test
    public void testParseDelegatorDefinitionUnmatchedOnMethodNameException() {
        final String expectedErrorMessage = "Unable to find invocable method " + METHOD_DOES_NOT_EXIST_NAME + " with 0 parameters on class = " + LIBRARY_CLASS_NO_ARG_CONS_NAME;
        expectedException.expect(DelegatorConfigurationException.class);
        expectedException.expectMessage(expectedErrorMessage);
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME + "," + METHOD_DOES_NOT_EXIST_NAME + "," + UNDEFINED_RETURN_TYPE;
        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
    }

    @Test
    public void testParseDelegatorDefinitionNoParams() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME + "," + METHOD_WITH_NO_PARAMS_NAME + "," + UNDEFINED_RETURN_TYPE;

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), Matchers.is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), Matchers.is(METHOD_WITH_NO_PARAMS_NAME));
        assertThat(configuredEntry.getReturnType(), Matchers.is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), emptyArray());
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void testParseDelegatorDefinitionOneParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME + "," + METHOD_WITH_ONE_PARAM_NAME + "," + UNDEFINED_RETURN_TYPE + "," + "param1";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), Matchers.is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), Matchers.is(METHOD_WITH_ONE_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), Matchers.is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void testParseDelegatorDefinitionTwoParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME + "," + METHOD_WITH_TWO_PARAM_NAME + "," + UNDEFINED_RETURN_TYPE + "," + "param1,param2";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), Matchers.is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), Matchers.is(METHOD_WITH_TWO_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), Matchers.is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1", "param2"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void testParseDelegatorDefinitionThreeParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME + "," + METHOD_WITH_THREE_PARAM_NAME + "," + UNDEFINED_RETURN_TYPE + "," + "param1,param2,param3";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey, sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), Matchers.is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), Matchers.is(METHOD_WITH_THREE_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), Matchers.is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1", "param2", "param3"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    //TODO add in varying return types inc void
}
