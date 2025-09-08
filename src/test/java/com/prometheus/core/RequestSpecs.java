package com.prometheus.core;

import com.prometheus.config.ConfigurationReader;
import com.prometheus.logging.ConsoleLogger;
import com.prometheus.logging.FileLogger;
import com.prometheus.logging.ILogger;
import com.prometheus.logging.NoLogger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {
        private static ILogger buildLogger() {
            String target = String.valueOf(ConfigurationReader.getProperty("log.target")).toLowerCase();
            switch (target) {
                case "file":
                    String path = ConfigurationReader.getProperty("log.file");
                    if (path == null || path.isBlank()) path = "target/restassured.log";
                    return new FileLogger(path);
                case "none":
                    return new NoLogger();
                case "console":
                default:
                    return new ConsoleLogger();
            }
        }

        private static final ILogger LOGGER = buildLogger();

        public static RequestSpecification defaultSpec() {
            return new RequestSpecBuilder()
                    .setBaseUri(ConfigurationReader.getProperty("base.url"))
                    .setBasePath(ConfigurationReader.getProperty("base.path"))
                    .addHeader("Accept",  ConfigurationReader.getProperty("accept.type"))
                    .addHeader("Content-Type", ConfigurationReader.getProperty("content.type"))
                    // central, ILogger-driven logging:
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL,  LOGGER.stream()))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL, LOGGER.stream()))
                    .build();
        }
}
