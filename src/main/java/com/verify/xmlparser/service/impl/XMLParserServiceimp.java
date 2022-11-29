package com.verify.xmlparser.service.impl;

import com.verify.exceptions.ServiceException;
import com.verify.payload.DataResponse;
import com.verify.xmlparser.entity.UserDetails;
import com.verify.xmlparser.service.XMLParserService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.UUID;

import static com.verify.exceptions.ErrorCodes.INVALID_Credentials;

@Service
@Log4j2
@CacheConfig(cacheNames = {"userId"})
public class XMLParserServiceimp implements XMLParserService {
    Logger logger = LoggerFactory.getLogger(XMLParserServiceimp.class);
    private URLConnection uc;
    private String tokens;

    private void gitHubCredentials() {
        URL url;
        String username = "sivalingam160599@gmail.com", password = "Sivalingam@#&16";
        try {
            url = new URL("https://raw.githubusercontent.com/SivalingamPalavesam/FileParsing-XML-/main/userDetails.xml");
            uc = url.openConnection();
            uc.setRequestProperty("X-Requested-With", "Curl");
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));//needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);
        } catch (IOException ex) {
            throw new ServiceException(INVALID_Credentials.getErrorCode(), INVALID_Credentials.getErrorDesc());
        }
    }

    @Override
    @Cacheable()
    public ResponseEntity<DataResponse> xmlParser(UserDetails userDetails) throws IOException {
        gitHubCredentials();
        getUserById("5");
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line = null;
        String file = null;
        file = null;

        while ((line = reader.readLine()) != null) {
            file = file + line + " ";
        }
        Document doc = Jsoup.parse(file);
        Elements data = doc.getElementsByTag("user");
        int length = data.size();
        int s = length - 1;
        for (int i = 0; i < length; i++) {
            String attrIdVal = doc.getElementsByTag("id").get(i).val();
            String attrNameVal = doc.getElementsByTag("name").get(i).val();
            String attrcityVal = doc.getElementsByTag("city").get(i).val();
            String attrStateVal = doc.getElementsByTag("state").get(i).val();

            if (attrNameVal.equals(userDetails.getName()) && attrStateVal.equals(userDetails.getState()) && attrcityVal.equals(userDetails.getCity())) {

                logger.info("DataFound  :" + "[ " + attrIdVal + " , " + attrNameVal + " , " + attrcityVal + " , " + attrStateVal + " ]");
                return new ResponseEntity<>(new DataResponse(true, "User detail was Found", tokens), HttpStatus.OK);

            } else {
                if (s == i && (!userDetails.getName().toLowerCase().equals(attrNameVal) || (!userDetails.getState().equals(attrStateVal)) || (!attrcityVal.equals(userDetails.getCity())))) {
                    logger.error("User detail Was NotFound");
                    return new ResponseEntity<>(new DataResponse(false, "User detail was Not Found"), HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(new DataResponse(true, "User detail was  Found"), HttpStatus.OK);
    }
    @Cacheable
    public String getUserById(final String id) {
        logger.info("Finding product by reference id = {}", id);
        StringBuilder token = new StringBuilder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         tokens = token.append(id).append("-")
                .append(UUID.randomUUID()).toString();
        System.out.println(tokens);
        return "The Token Is  : "+tokens;
    }
}