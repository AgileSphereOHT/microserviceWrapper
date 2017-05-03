package uk.co.agilesphere.wrapper.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatorInvocationException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(uk.co.agilesphere.wrapper.delegator.DelegatorInvocationException.class);

    public DelegatorInvocationException() {
    }

    public DelegatorInvocationException(String message) {
        super(message);
    }

    public DelegatorInvocationException(Throwable cause) {
        super(cause);
    }

    public DelegatorInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelegatorInvocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
