package com.apitesting.datingapp.base;

import com.apitesting.datingapp.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class BaseApiTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseApiTest.class);
    protected static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUp() {
        logger.info("🚀 Initializing API Test Framework...");
        logger.info("Base URL: {}", TestConfig.getBaseUrl());

        RestAssured.baseURI = TestConfig.getBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        logger.info("✅ Test framework initialized successfully");
    }

    protected RequestSpecification givenAuth() {
        logger.debug("Creating authenticated request specification");
        return given().spec(requestSpec);
    }
}