package com.prometheus.core;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseClient {
    protected final RequestSpecification spec;

    protected BaseClient(RequestSpecification spec){
        this.spec = spec;
    }

    protected Response get(String path) {
        return RestAssured.given().spec(spec).when().get(path);
    }
    protected Response post(String path, Object body){
        return RestAssured.given().spec(spec).body(body).when().post(path);
    }
    protected Response put(String path, Object body){
        return RestAssured.given().spec(spec).body(body).when().put(path);
    }
    protected Response delete(String path){
        return RestAssured.given().spec(spec).when().delete(path);
    }

}
