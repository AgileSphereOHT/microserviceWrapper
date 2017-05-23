package uk.co.agilesphere.microservicewrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.agilesphere.microservicewrapper.delegator.exception.DelegatorInvocationException;

import java.util.*;

public class DelegatorResponder {

    private static Logger logger = LoggerFactory.getLogger(DelegatorResponder.class);

    private DelegatorRegistry registry;

    public DelegatorResponder(DelegatorRegistry registry) {
        this.registry = registry;
    }

    public String getResult(String key, Map<String, String[]> paramMap) {
        String rtn;
        Set<String> paramNames = paramMap.keySet();
        int paramCount = paramNames.size();
        String[] params = new String[paramCount];

        Iterator it = paramNames.iterator();
        int ix = 0;
        while (it.hasNext()) {
            String paramName = (String) it.next();
            String[] paramValues = paramMap.get(paramName);
            logger.info("Parameter " + ix + " name = " + paramName + " value = " + paramValues[0]);
            params[ix] = paramValues[0];
            ix++;
        }

        return getResponse(key, params);
    }

    private String getResponse(String key, String[] params) {
        String returnString;

        String returnType = registry.getEntry(key).getReturnType();

        Object invocationResponse = invokeDelegator(key, params);

        switch (returnType) {
            case "Array":
                returnString = returnArray((String[]) invocationResponse);
                break;
            case "List":
                returnString = returnList((List<String>) invocationResponse);
                break;
            case "Map":
                returnString = returnMap((Map<String, String>) invocationResponse);
                break;
            case "String":
                returnString = (String) invocationResponse;
                break;
            default:
                String expectedErrorMessage = "Unable to handle invoked method return type for look up key " + key + " with intended return type = " + returnType;
                logger.error(expectedErrorMessage);
                throw new DelegatorInvocationException(expectedErrorMessage);
        }
        return returnString;
    }

    private Object invokeDelegator(String key, String... parameters) {
        DelegatorRegistryEntry entry = registry.getEntry(key);
        logger.info("Registry Entry found = " + entry.getKey());
        return entry.getDelegator().invokeMethod(parameters);
    }

    private String returnArray(String[] returnedArray) {
        return Arrays.toString(returnedArray);
    }

    private String returnList(List<String> returnedList) {
        StringBuilder sb = new StringBuilder();
        for (String s : returnedList) {
            sb.append(s);
            sb.append(",").append(' ');
            ;
        }
        return sb.toString();
    }

    private String returnMap(Map<String, String> returnedMap) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iter = returnedMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }
}
