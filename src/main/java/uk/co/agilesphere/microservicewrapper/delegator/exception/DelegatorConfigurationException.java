package uk.co.agilesphere.microservicewrapper.delegator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatorConfigurationException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(DelegatorConfigurationException.class);

    public DelegatorConfigurationException() {
    }

    public DelegatorConfigurationException(String message) {
        super(message);
    }

    public DelegatorConfigurationException(Throwable cause) {
        super(cause);
    }

    public DelegatorConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelegatorConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
