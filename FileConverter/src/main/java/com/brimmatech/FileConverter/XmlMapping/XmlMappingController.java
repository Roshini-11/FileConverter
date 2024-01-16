package com.brimmatech.FileConverter.XmlMapping;

import com.brimmatech.FileConverter.responseHandling.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class XmlMappingController {
    private XmlMappingService xmlMappingService;

    @PostMapping("/save/xmlMapping")
    public ResponseEntity<ResponseMessage<String>> fileSaver(@RequestParam MultipartFile file){
        try{
            String response = xmlMappingService.parseXml(file);
            return ResponseEntity.ok(new ResponseMessage<>(HttpStatus.OK.value(), "Success", response));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
