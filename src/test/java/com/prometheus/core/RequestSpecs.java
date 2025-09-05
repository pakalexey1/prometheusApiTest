package com.prometheus.core;

import com.prometheus.config.ConfigurationReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {
    public static RequestSpecification defaultSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(ConfigurationReader.getProperty("base.url"))
                .setBasePath(ConfigurationReader.getProperty("base.path"))
                .addHeader("Accept",ConfigurationReader.getProperty("accept.type"))
                .addHeader("Content-Type",ConfigurationReader.getProperty("content.type"))
                .build();
    }
}
