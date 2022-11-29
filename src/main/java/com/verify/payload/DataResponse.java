package com.verify.payload;

import lombok.*;

@Getter
@Setter
public class DataResponse {
    private Boolean success;
    private String message;
    private String token;

    public DataResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public DataResponse(Boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public DataResponse() {
    }
}
