package com.micro.auth.model;

public class RefreshTokenResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public RefreshTokenResponse() {
    }

    public RefreshTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public RefreshTokenResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

