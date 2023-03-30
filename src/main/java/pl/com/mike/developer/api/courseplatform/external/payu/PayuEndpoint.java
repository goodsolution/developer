package pl.com.mike.developer.api.courseplatform.external.payu;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.courseplatform.CourseOrdersService;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/crs/payu")
public class PayuEndpoint {

    private CourseOrdersService courseOrdersService;
    private ApplicationConfig applicationConfig;

    public PayuEndpoint(CourseOrdersService courseOrdersService, ApplicationConfig applicationConfig) {
        this.courseOrdersService = courseOrdersService;
        this.applicationConfig = applicationConfig;
    }

    @PostMapping("/notify")
    public void processPayUNotify(@RequestHeader("OpenPayu-Signature") String openPayuSignature, @RequestBody String jsonBody){
        verifyNotification(openPayuSignature, jsonBody);
        JsonNode requestBody = prepareJsonNode(jsonBody);
        courseOrdersService.processPayuNotify(requestBody.get("order").get("extOrderId").asLong(), requestBody.get("order").get("status").asText());
    }

    private void verifyNotification(String openPayuSignature, String jsonBody) {
        String incomingSignature = extractSignature(openPayuSignature);
        String hashFunction = extractHashFunction(openPayuSignature);
        String concatenated = jsonBody + applicationConfig.getPaymentsPayuSecondKey();
        String expectedSignature = hash(concatenated, hashFunction);
        compareSignatures(expectedSignature, incomingSignature);
    }

    private String extractSignature(String openPayuSignature) {
        Pattern pattern = Pattern.compile("signature=(.*?);");
        Matcher matcher = pattern.matcher(openPayuSignature);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("No signature");
        }
    }

    private String extractHashFunction(String openPayuSignature) {
        Pattern pattern = Pattern.compile("algorithm=(.*?);");
        Matcher matcher = pattern.matcher(openPayuSignature);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("No hash algorithm");
        }
    }

    private String hash(String text, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(text.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Wrong hash algorithm");
        }
    }

    private void compareSignatures(String expectedSignature, String incomingSignature) {
        if(!expectedSignature.equals(incomingSignature)) {
            throw new IllegalArgumentException("Wrong signature");
        }
    }

    private JsonNode prepareJsonNode(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Error during json parsing");
        }
    }

}
