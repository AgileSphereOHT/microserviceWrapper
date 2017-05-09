package uk.co.agilesphere.microservicewrapper.delegator;

public class TestConstants {

    public static final String LIBRARY_CLASS_NO_ARG_CONS_NAME = "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassNoArgConstructor";
    public static final String LIBRARY_CLASS_WITH_ARG_CONS_NAME = "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassWithArgConstructor";
    public static final String LIBRARY_CLASS_WITH_PRIVATE_CONS_NAME = "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassWithPrivateConstructor";
    public static final String LIBRARY_CLASS_DOES_NOT_EXIST_NAME = "uk.co.agilesphere.microservicewrapper.delegator.LibraryClassDoesNotExist";
    public static final String METHOD_WITH_NO_PARAMS_NAME = "respond";
    public static final String METHOD_WITH_ONE_PARAM_NAME = "respondOneParamAsString";
    public static final String METHOD_WITH_TWO_PARAM_NAME = "respondTwoParamAsString";
    public static final String METHOD_WITH_THREE_PARAM_NAME = "respondThreeParamAsString";
    public static final String METHOD_WITH_THREE_PARAM_AS_ARRAY_NAME = "respondThreeParamAsArray";
    public static final String METHOD_WITH_THREE_PARAM_AS_LIST_NAME = "respondThreeParamAsList";
    public static final String METHOD_WITH_THREE_PARAM_AS_MAP_NAME = "respondThreeParamAsMap";
    public static final String METHOD_THROWING_SERVICE_EXCEPTION_NAME = "respondWithException";
    public static final String METHOD_DOES_NOT_EXIST_NAME = "methoddoesnotexist";
    public static final String UNDEFINED_RETURN_TYPE = "UNDEFINED";


    public static final String[] ZERO_PARAMETERS = {};
    public static final String[] ONE_PARAMETER = {"param1"};
    public static final String[] TWO_PARAMETERS = {"param1","param2"};
    public static final String[] THREE_PARAMETERS =  {"param1","param2","param3"};
    //static final Class[] tooManyParameters = new Class[]{String.class, String.class, String.class, String.class};
}
