package com.apitesting.datingapp.tests;

import com.apitesting.datingapp.base.BaseApiTest;
import com.apitesting.datingapp.endpoints.UserEndpoint;
import com.apitesting.datingapp.endpoints.UsersEndpoint;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Negative API Tests")
public class NegativeTests extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(NegativeTests.class);

    @Test
    @DisplayName("NT-001: Test gender=magic - should return 500 (BUG-020)")
    void testGenderMagicReturns500() {
        logger.info("🧪 Starting test: gender=magic (BUG-020)");

        Response response = UsersEndpoint.getUsersByGenderRaw("magic");
        logger.info("✅ Received response for gender=magic: status={}", response.getStatusCode());

        assertEquals(500, response.getStatusCode(),
                "Should return 500 for gender=magic (BUG-020)");

        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains("Internal Server Error");

        logger.info("🎯 Test completed: Confirmed 500 error for gender=magic");
    }

    @Test
    @DisplayName("NT-002: Test gender=McCloud - should return 500 (BUG-021)")
    void testGenderMcCloudReturns500() {
        logger.info("🧪 Starting test: gender=McCloud (BUG-021)");

        Response response = UsersEndpoint.getUsersByGenderRaw("McCloud");
        logger.info("✅ Received response for gender=McCloud: status={}", response.getStatusCode());

        assertEquals(500, response.getStatusCode(),
                "Should return 500 for gender=McCloud (BUG-021)");

        logger.info("🎯 Test completed: Confirmed 500 error for gender=McCloud");
    }

    @Test
    @DisplayName("NT-010: Test user ID=0 - should return 500 (BUG-006)")
    void testUserIdZero() {
        logger.info("🧪 Starting test: user ID=0 (BUG-006)");

        Response response = UserEndpoint.getUserByIdRaw(0);
        logger.info("✅ Received response for ID=0: status={}", response.getStatusCode());

        assertEquals(500, response.getStatusCode(),
                "Should return 500 for ID=0 (BUG-006)");

        logger.info("🎯 Test completed: Confirmed 500 error for ID=0");
    }

    @Test
    @DisplayName("NT-011: Test user ID=-1 - should return 200 with null user (BUG-007)")
    void testUserIdNegative() {
        logger.info("🧪 Starting test: user ID=-1 (BUG-007)");

        Response response = UserEndpoint.getUserByIdRaw(-1);
        logger.info("✅ Received response for ID=-1: status={}", response.getStatusCode());

        assertEquals(200, response.getStatusCode(),
                "Should return 200 for ID=-1 (BUG-007)");

        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains("\"user\":null");

        logger.info("🎯 Test completed: Confirmed 200 with null user for ID=-1");
    }

    @Test
    @DisplayName("NT-012: Test very large user ID - should return wrong user (BUG-008)")
    void testVeryLargeUserId() {
        logger.info("🧪 Starting test: very large user ID (BUG-008)");

        Response response = UserEndpoint.getUserByIdRaw(999999999);
        logger.info("✅ Received response for large ID: status={}", response.getStatusCode());

        assertEquals(200, response.getStatusCode(),
                "Should return 200 for large ID (BUG-008)");

        // This should return user with ID=911 according to bug report
        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains("\"id\":911");

        logger.info("🎯 Test completed: Confirmed wrong user returned for large ID");
    }

    @Test
    @DisplayName("NT-013: Test minimal valid user ID=1 - should return null user (BUG-009)")
    void testMinimalUserId() {
        logger.info("🧪 Starting test: minimal user ID=1 (BUG-009)");

        Response response = UserEndpoint.getUserByIdRaw(1);
        logger.info("✅ Received response for ID=1: status={}", response.getStatusCode());

        assertEquals(200, response.getStatusCode());

        String responseBody = response.getBody().asString();
        assertThat(responseBody).contains("\"user\":null");

        logger.info("🎯 Test completed: Confirmed null user for ID=1");
    }


}