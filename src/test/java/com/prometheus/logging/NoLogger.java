package com.prometheus.logging;

import java.io.PrintStream;

public class NoLogger implements ILogger{
    private static final PrintStream NULL = new PrintStream(java.io.OutputStream.nullOutputStream());
    @Override
    public void info(String msg) {

    }

    @Override
    public void error(String msg, Throwable t) {

    }

    @Override public PrintStream stream() {
        return NULL;
    }
}
