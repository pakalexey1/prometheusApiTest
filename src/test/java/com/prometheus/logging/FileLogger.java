package com.prometheus.logging;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class FileLogger implements ILogger{
    private final PrintStream out;
    public FileLogger(String path) {
        try {
            this.out = new PrintStream(new FileOutputStream(path, true), true, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Cannot open log file: " + path, e);
        }
    }
    @Override public void info(String msg) { out.println(msg); }
    @Override public void error(String msg, Throwable t) { out.println(msg); t.printStackTrace(out); }
    @Override public PrintStream stream() { return out; }
    @Override public void close() { out.close(); }
}
