package com.prometheus.logging;

import java.io.PrintStream;

public class ConsoleLogger implements ILogger{
    private final PrintStream out = System.out;
    @Override
    public void info(String msg) {
        out.println(msg);
    }

    @Override
    public void error(String msg, Throwable t) {
        out.println(msg); t.printStackTrace(out);
    }

    @Override
    public PrintStream stream() {
        return out;
    }

}
