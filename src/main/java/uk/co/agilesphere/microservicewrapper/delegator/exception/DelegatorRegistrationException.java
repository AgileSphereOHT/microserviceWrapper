package uk.co.agilesphere.microservicewrapper.delegator.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatorRegistrationException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(DelegatorRegistrationException.class);

    public DelegatorRegistrationException() {
    }

    public DelegatorRegistrationException(String message) {
        super(message);
    }

    public DelegatorRegistrationException(Throwable cause) {
        super(cause);
    }

    public DelegatorRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelegatorRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
