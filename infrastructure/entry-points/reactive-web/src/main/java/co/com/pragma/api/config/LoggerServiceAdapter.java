package co.com.pragma.api.config;

import co.com.pragma.model.approvedloans.gateways.LoggerPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LoggerServiceAdapter implements LoggerPort {
    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
