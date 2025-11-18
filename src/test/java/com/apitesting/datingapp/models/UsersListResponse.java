package com.apitesting.datingapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UsersListResponse {
    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    @JsonProperty("errorCode")
    private Integer errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("idList")
    private List<Integer> idList;

    public Boolean getIsSuccess() { return isSuccess; }
    public void setIsSuccess(Boolean isSuccess) { this.isSuccess = isSuccess; }

    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public List<Integer> getIdList() { return idList; }
    public void setIdList(List<Integer> idList) { this.idList = idList; }
}
