package uk.co.agilesphere.delegator;

public class LibraryClassNoArgConstructor {

    private String returnValue = "RETURNED";

    public LibraryClassNoArgConstructor() {
    }

    public String respond() {
        return returnValue;
    }

}