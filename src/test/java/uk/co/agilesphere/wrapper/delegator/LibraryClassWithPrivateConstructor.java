package uk.co.agilesphere.wrapper.delegator;

public class LibraryClassWithPrivateConstructor {
    private String returnValue = "RETURNED";

    private LibraryClassWithPrivateConstructor(String providedReturnValue) {
        returnValue = providedReturnValue;
    }

    public String respond() {
        return returnValue;
    }
}
