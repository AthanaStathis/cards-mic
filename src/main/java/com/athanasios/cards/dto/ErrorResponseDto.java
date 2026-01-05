package com.athanasios.cards.dto;

public class ErrorResponseDto {
    private String apiPath;
    private String errorStatusCode;
    private String errorMessage;

    public ErrorResponseDto(String apiPath, String errorStatusCode, String errorMessage) {
        this.apiPath = apiPath;
        this.errorStatusCode = errorStatusCode;
        this.errorMessage = errorMessage;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getErrorStatusCode() {
        return errorStatusCode;
    }

    public void setErrorStatusCode(String errorStatusCode) {
        this.errorStatusCode = errorStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
