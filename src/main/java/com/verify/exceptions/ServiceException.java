package com.verify.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ServiceException   extends RuntimeException{
    private String errorCode;
    private String errorDesc;

    public ServiceException(String errorCode) {
    }
}
