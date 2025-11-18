package com.apitesting.datingapp.endpoints;

import com.apitesting.datingapp.models.ApiResponse;
import com.apitesting.datingapp.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserEndpoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ApiResponse<User> getUserById(Integer id) {
        Response response = getUserByIdRaw(id);

        try {
            // Десериализуем JSON в Map для анализа
            Map<String, Object> responseMap = mapper.readValue(response.getBody().asString(),
                    new TypeReference<Map<String, Object>>() {});

            ApiResponse<User> apiResponse = new ApiResponse<>();

            // Устанавливаем основные поля
            if (responseMap.containsKey("isSuccess")) {
                apiResponse.setIsSuccess((Boolean) responseMap.get("isSuccess"));
            }
            if (responseMap.containsKey("errorCode")) {
                apiResponse.setErrorCode(((Number) responseMap.get("errorCode")).intValue());
            }
            if (responseMap.containsKey("errorMessage")) {
                apiResponse.setErrorMessage((String) responseMap.get("errorMessage"));
            }

            // Обрабатываем поле user
            if (responseMap.containsKey("user") && responseMap.get("user") != null) {
                Object userObj = responseMap.get("user");
                if (userObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> userMap = (Map<String, Object>) userObj;
                    User user = mapToUser(userMap);
                    apiResponse.setData(user);
                } else {
                    apiResponse.setData(null);
                }
            } else {
                apiResponse.setData(null);
            }

            return apiResponse;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response for user ID: " + id, e);
        }
    }

    private static User mapToUser(Map<String, Object> userMap) {
        User user = new User();

        if (userMap.containsKey("id")) {
            Object idObj = userMap.get("id");
            if (idObj instanceof Number) {
                user.setId(((Number) idObj).intValue());
            }
        }
        if (userMap.containsKey("name")) {
            user.setName((String) userMap.get("name"));
        }
        if (userMap.containsKey("gender")) {
            user.setGender((String) userMap.get("gender"));
        }
        if (userMap.containsKey("age")) {
            Object ageObj = userMap.get("age");
            if (ageObj instanceof Number) {
                user.setAge(((Number) ageObj).intValue());
            }
        }
        if (userMap.containsKey("city")) {
            user.setCity((String) userMap.get("city"));
        }
        if (userMap.containsKey("registrationDate")) {
            user.setRegistrationDate((String) userMap.get("registrationDate"));
        }

        return user;
    }

    public static Response getUserByIdRaw(Integer id) {
        if (id == null) {
            // Для null ID отправляем запрос без path параметра
            return given()
                    .when()
                    .get("/user/");
        }
        return given()
                .pathParam("id", id)
                .when()
                .get("/user/{id}");
    }

    public static Response getUserByIdWithInvalidAcceptHeader(Integer id) {
        return given()
                .pathParam("id", id)
                .header("Accept", "text/xml")
                .when()
                .get("/user/{id}");
    }

    // Альтернативный метод для прямой десериализации через ObjectMapper
    public static ApiResponse<User> getUserByIdAlternative(Integer id) {
        Response response = getUserByIdRaw(id);

        try {
            // Прямая десериализация с указанием типов
            return mapper.readValue(response.getBody().asString(),
                    new TypeReference<ApiResponse<User>>() {});
        } catch (Exception e) {
            System.err.println("Alternative method failed, using manual parsing for ID: " + id);
            return getUserById(id); // Fallback to manual parsing
        }
    }
}