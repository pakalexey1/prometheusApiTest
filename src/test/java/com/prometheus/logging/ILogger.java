package com.prometheus.logging;

import java.io.PrintStream;

public interface ILogger extends AutoCloseable {
    void info(String msg);
    void error(String msg, Throwable t);
    PrintStream stream();  // needed by RestAssured's filters
    @Override default void close() {}
}