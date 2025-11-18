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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Data Integrity Tests")
public class DataTests extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(DataTests.class);

    @Test
    @DisplayName("DT-001: Verify gender consistency between endpoints")
    void testGenderConsistency() {
        logger.info("🧪 Starting test: Gender consistency between endpoints");

        // Test for male users
        UsersListResponse maleUsers = UsersEndpoint.getUsersByGender("male");
        assertTrue(maleUsers.getIsSuccess(), "Should successfully get male users");
        logger.info("📊 Found {} male users", maleUsers.getIdList().size());

        // Check first 5 male users for gender consistency
        List<Integer> maleUserIds = maleUsers.getIdList().subList(0,
                Math.min(5, maleUsers.getIdList().size()));

        int checkedUsers = 0;
        for (Integer userId : maleUserIds) {
            ApiResponse<User> userResponse = UserEndpoint.getUserById(userId);
            if (userResponse.getData() != null) {
                User user = userResponse.getData();
                logger.debug("Checking user {}: gender={}", userId, user.getGender());
                assertThat(user.getGender()).as("User %d should have gender 'male'", userId)
                        .isEqualTo("male");
                checkedUsers++;
            }
        }

        logger.info("✅ Checked gender consistency for {} male users", checkedUsers);

        // Test for female users
        UsersListResponse femaleUsers = UsersEndpoint.getUsersByGender("female");
        assertTrue(femaleUsers.getIsSuccess(), "Should successfully get female users");
        logger.info("📊 Found {} female users", femaleUsers.getIdList().size());

        // Check first 5 female users for gender consistency
        List<Integer> femaleUserIds = femaleUsers.getIdList().subList(0,
                Math.min(5, femaleUsers.getIdList().size()));

        checkedUsers = 0;
        for (Integer userId : femaleUserIds) {
            ApiResponse<User> userResponse = UserEndpoint.getUserById(userId);
            if (userResponse.getData() != null) {
                User user = userResponse.getData();
                logger.debug("Checking user {}: gender={}", userId, user.getGender());
                assertThat(user.getGender()).as("User %d should have gender 'female'", userId)
                        .isEqualTo("female");
                checkedUsers++;
            }
        }

        logger.info("✅ Checked gender consistency for {} female users", checkedUsers);
        logger.info("🎯 Gender consistency test completed");
    }

    @Test
    @DisplayName("DT-010: Test business validation for user 911")
    void testBusinessValidationForUser911() {
        logger.info("🧪 Starting test: Business validation for user 911");

        ApiResponse<User> response = UserEndpoint.getUserById(911);
        logger.info("✅ Received response for user 911");

        assertTrue(response.getIsSuccess(), "Should successfully get user 911");
        assertNotNull(response.getData(), "User data should not be null");

        User user = response.getData();
        logger.info("👤 User 911 data: age={}, gender={}, city={}, regDate={}",
                user.getAge(), user.getGender(), user.getCity(), user.getRegistrationDate());

        // Validate age is realistic
        assertThat(user.getAge())
                .as("Age should be realistic (18-100)")
                .isBetween(18, 100);

        // Validate ID ≠ age
        assertThat(user.getId())
                .as("User ID should not equal age")
                .isNotEqualTo(user.getAge());

        // Validate registration date is not in the future and format is correct
        String registrationDate = user.getRegistrationDate();
        assertThat(registrationDate)
                .as("Registration date should be in valid format")
                .matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*");

        logger.info("🎯 Business validation test completed for user 911");
    }


}