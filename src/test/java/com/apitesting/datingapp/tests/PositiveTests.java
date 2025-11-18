package com.apitesting.datingapp.tests;

import com.apitesting.datingapp.base.BaseApiTest;
import com.apitesting.datingapp.endpoints.UserEndpoint;
import com.apitesting.datingapp.endpoints.UsersEndpoint;
import com.apitesting.datingapp.models.ApiResponse;
import com.apitesting.datingapp.models.User;
import com.apitesting.datingapp.models.UsersListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Positive API Tests")
public class PositiveTests extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(PositiveTests.class);

    @Test
    @DisplayName("PT-001: Get male users IDs")
    void testGetMaleUsers() {
        logger.info("🧪 Starting test: Get male users IDs");

        UsersListResponse response = UsersEndpoint.getUsersByGender("male");

        logger.info("✅ Received response for male users");
        logger.debug("Response: isSuccess={}, errorCode={}, userCount={}",
                response.getIsSuccess(), response.getErrorCode(),
                response.getIdList() != null ? response.getIdList().size() : 0);

        assertAll("Male users response validation",
                () -> assertTrue(response.getIsSuccess(), "isSuccess should be true"),
                () -> assertEquals(0, response.getErrorCode(), "errorCode should be 0"),
                () -> assertNull(response.getErrorMessage(), "errorMessage should be null"),
                () -> assertNotNull(response.getIdList(), "idList should not be null"),
                () -> assertFalse(response.getIdList().isEmpty(), "idList should not be empty")
        );

        // Log first few IDs for verification
        logger.info("📋 First 5 male user IDs: {}",
                response.getIdList().subList(0, Math.min(5, response.getIdList().size())));

        logger.info("🎯 Test completed successfully: Found {} male users", response.getIdList().size());
    }

    @Test
    @DisplayName("PT-002: Get female users IDs")
    void testGetFemaleUsers() {
        logger.info("🧪 Starting test: Get female users IDs");

        UsersListResponse response = UsersEndpoint.getUsersByGender("female");

        logger.info("✅ Received response for female users");

        assertAll("Female users response validation",
                () -> assertTrue(response.getIsSuccess(), "isSuccess should be true"),
                () -> assertEquals(0, response.getErrorCode(), "errorCode should be 0"),
                () -> assertNull(response.getErrorMessage(), "errorMessage should be null"),
                () -> assertNotNull(response.getIdList(), "idList should not be null"),
                () -> assertFalse(response.getIdList().isEmpty(), "idList should not be empty")
        );

        logger.info("🎯 Test completed successfully: Found {} female users", response.getIdList().size());
    }

    @Test
    @DisplayName("PT-004A: Get user data by valid ID")
    void testGetUserByValidId() {
        logger.info("🧪 Starting test: Get user data by valid ID");

        // First get male users to find a valid ID
        UsersListResponse usersResponse = UsersEndpoint.getUsersByGender("male");
        assertFalse(usersResponse.getIdList().isEmpty(), "Should have male users");

        Integer firstUserId = usersResponse.getIdList().get(0);
        logger.info("🔍 Testing with user ID: {}", firstUserId);

        ApiResponse<User> userResponse = UserEndpoint.getUserById(firstUserId);

        logger.info("✅ Received user data response");
        logger.debug("User response: isSuccess={}, errorCode={}",
                userResponse.getIsSuccess(), userResponse.getErrorCode());

        assertAll("Valid user data response validation",
                () -> assertTrue(userResponse.getIsSuccess(), "isSuccess should be true"),
                () -> assertEquals(0, userResponse.getErrorCode(), "errorCode should be 0"),
                () -> assertNull(userResponse.getErrorMessage(), "errorMessage should be null")
        );

        if (userResponse.getData() != null) {
            User user = userResponse.getData();
            logger.info("👤 User data retrieved: ID={}, Name={}, Gender={}",
                    user.getId(), user.getName(), user.getGender());

            assertAll("User data validation",
                    () -> assertNotNull(user.getId(), "User ID should not be null"),
                    () -> assertNotNull(user.getName(), "User name should not be null"),
                    () -> assertNotNull(user.getGender(), "User gender should not be null"),
                    () -> assertNotNull(user.getAge(), "User age should not be null"),
                    () -> assertNotNull(user.getCity(), "User city should not be null"),
                    () -> assertNotNull(user.getRegistrationDate(), "Registration date should not be null")
            );
        } else {
            logger.warn("⚠️ User data is null for ID: {}", firstUserId);
        }

        logger.info("🎯 Test completed successfully");
    }

    @Test
    @DisplayName("PT-005: Validate user response structure")
    void testUserResponseStructure() {
        logger.info("🧪 Starting test: Validate user response structure");

        ApiResponse<User> response = UserEndpoint.getUserById(10);
        logger.info("✅ Received response for structure validation");

        assertAll("Response structure validation",
                () -> assertTrue(response.getIsSuccess(), "isSuccess should be present"),
                () -> assertNotNull(response.getErrorCode(), "errorCode should be present"),
                () -> assertTrue(response.getErrorMessage() == null || response.getErrorMessage() instanceof String,
                        "errorMessage should be null or string"),
                () -> assertNotNull(response.getData(), "user field should be present")
        );

        if (response.getData() != null) {
            User user = response.getData();
            logger.debug("User object structure validated: {}", user);

            assertAll("User object structure validation",
                    () -> assertThat(user.getId()).isInstanceOf(Integer.class),
                    () -> assertThat(user.getName()).isInstanceOf(String.class),
                    () -> assertThat(user.getGender()).isInstanceOf(String.class),
                    () -> assertThat(user.getAge()).isInstanceOf(Integer.class),
                    () -> assertThat(user.getCity()).isInstanceOf(String.class),
                    () -> assertThat(user.getRegistrationDate()).isInstanceOf(String.class)
            );
        }

        logger.info("🎯 Response structure validation completed successfully");
    }
}