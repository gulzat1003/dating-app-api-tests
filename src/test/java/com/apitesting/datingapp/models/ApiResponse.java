package com.apitesting.datingapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    @JsonProperty("errorCode")
    private Integer errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("user")
    private T data;

    // Конструкторы
    public ApiResponse() {}

    public ApiResponse(Boolean isSuccess, Integer errorCode, String errorMessage, T data) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    // Геттеры и сеттеры
    public Boolean getIsSuccess() { return isSuccess; }
    public void setIsSuccess(Boolean isSuccess) { this.isSuccess = isSuccess; }

    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    @Override
    public String toString() {
        return String.format("ApiResponse{isSuccess=%s, errorCode=%d, errorMessage='%s', data=%s}",
                isSuccess, errorCode, errorMessage, data);
    }
}