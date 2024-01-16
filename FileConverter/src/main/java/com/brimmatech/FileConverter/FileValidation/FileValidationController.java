package com.brimmatech.FileConverter.FileValidation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@Slf4j
@RequestMapping("/api")
public class FileValidationController {

    private final  ObjectMapper objectMapper;

    public FileValidationController(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @PostMapping("/validate-json")
    public ResponseEntity<String> validateJson(@RequestBody String jsonString) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            log.info("Given data is: {}" , jsonNode);
            return ResponseEntity.ok("Valid Json");
        }catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
