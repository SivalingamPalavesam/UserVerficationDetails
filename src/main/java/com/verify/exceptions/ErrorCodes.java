package com.verify.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {
    INVALID_Credentials("1", "Invalid github Credentials"),

    DATA_NOT_FOUND("2", "User Data not found ");


    private final String errorCode;
    private final String errorDesc;
}
