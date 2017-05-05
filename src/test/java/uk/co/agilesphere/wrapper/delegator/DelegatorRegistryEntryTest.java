package uk.co.agilesphere.wrapper.delegator;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.co.agilesphere.wrapper.delegator.TestConstants.*;

public class DelegatorRegistryEntryTest {

    @Test
    public void parseDelegatorDefinitionNoParams() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME+","+METHOD_WITH_NO_PARAMS_NAME+","+UNDEFINED_RETURN_TYPE;

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey,sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), is(METHOD_WITH_NO_PARAMS_NAME));
        assertThat(configuredEntry.getReturnType(), is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), emptyArray());
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));

    }

    @Test
    public void parseDelegatorDefinitionOneParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME+","+METHOD_WITH_ONE_PARAM_NAME+","+UNDEFINED_RETURN_TYPE+","+"param1";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey,sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), is(METHOD_WITH_ONE_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void parseDelegatorDefinitionTwoParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME+","+METHOD_WITH_TWO_PARAM_NAME+","+UNDEFINED_RETURN_TYPE+","+"param1,param2";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey,sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), is(METHOD_WITH_TWO_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1","param2"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }

    @Test
    public void parseDelegatorDefinitionThreeParam() {
        final String sampleKey = "keyValue";
        final String sampleDefinition = LIBRARY_CLASS_NO_ARG_CONS_NAME+","+METHOD_WITH_THREE_PARAM_NAME+","+UNDEFINED_RETURN_TYPE+","+"param1,param2,param3";

        DelegatorRegistryEntry configuredEntry = DelegatorRegistryEntry.generateFromPropertyDefinition(sampleKey,sampleDefinition);
        assertThat(configuredEntry.getKey(), is("keyValue"));
        assertThat(configuredEntry.getClassName(), is(LIBRARY_CLASS_NO_ARG_CONS_NAME));
        assertThat(configuredEntry.getMethodName(), is(METHOD_WITH_THREE_PARAM_NAME));
        assertThat(configuredEntry.getReturnType(), is(UNDEFINED_RETURN_TYPE));
        assertThat(configuredEntry.getParameterNames(), arrayContaining("param1","param2","param3"));
        assertThat(configuredEntry.getDelegator(), is(notNullValue()));
    }
}
