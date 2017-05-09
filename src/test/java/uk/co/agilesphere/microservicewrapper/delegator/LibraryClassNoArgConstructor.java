package uk.co.agilesphere.microservicewrapper.delegator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleEntry;

public class LibraryClassNoArgConstructor {

    private String returnValue = "RETURNED";

    public LibraryClassNoArgConstructor() { }

    public String respond() {
        return returnValue;
    }

    public String respondWithException() {
        throw new RuntimeException("A library class exception");
    }

    public String respondOneParamAsString(String param1) {
        return "Param1 = " + param1;
    }

    public String respondTwoParamAsString(String param1, String param2) {
        return "Params = " + param1 + "," + param2;
    }

    public String respondThreeParamAsString(String param1, String param2, String param3) {
        return "Params = " + param1 + "," + param2 + "," + param3;
    }

    public String[] respondThreeParamAsArray(String param1, String param2, String param3) {
        String[] returnArray = {param1, param2, param3};
        return returnArray;
    }

    public List<String> respondThreeParamAsList(String param1, String param2, String param3) {
        String[] returnArray = {param1, param2, param3};
        return new ArrayList<>(Arrays.asList(returnArray));
    }

    public Map<String, String> respondThreeParamAsMap(String param1, String param2, String param3) {
        final Stream<SimpleEntry<String, String>> entryStream = Stream.of(
                new SimpleEntry<>("param1", param1),
                new SimpleEntry<>("param2", param2),
                new SimpleEntry<>("param3", param3));
        return Collections.unmodifiableMap(entryStream.collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
    }

}