package com.verify.xmlparser.controller;

import com.verify.payload.DataResponse;
import com.verify.xmlparser.entity.UserDetails;
import com.verify.xmlparser.service.XMLParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class XMLParserController {

    @Autowired
    private XMLParserService xmlParserService;

    @GetMapping("/getUserValue")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<DataResponse> xmlParser(@RequestBody UserDetails userDetails) throws IOException {
        return xmlParserService.xmlParser(userDetails);
    }

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public String getUserById(@RequestParam("id") String id) {
        return   xmlParserService.getUserById(id);
    }
}
