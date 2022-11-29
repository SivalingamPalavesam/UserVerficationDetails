package com.verify.xmlparser.service;

import com.verify.payload.DataResponse;
import com.verify.xmlparser.entity.UserDetails;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface XMLParserService {
    public ResponseEntity<DataResponse> xmlParser(UserDetails userDetails) throws IOException;

   public String getUserById(String id);
}
