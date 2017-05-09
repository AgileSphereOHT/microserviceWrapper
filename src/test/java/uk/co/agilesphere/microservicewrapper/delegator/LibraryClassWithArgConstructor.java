package uk.co.agilesphere.microservicewrapper.delegator;

public class LibraryClassWithArgConstructor {
    private String returnValue = "RETURNED";

    public LibraryClassWithArgConstructor(String providedReturnValue) {
        returnValue = providedReturnValue;
    }

    public String respond() {
        return returnValue;
    }
}
