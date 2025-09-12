package co.com.pragma.model.approvedloans.gateways;

public interface LoggerPort {
    void info(String message);
    void debug(String message);
    void error(String message, Throwable throwable);
}
