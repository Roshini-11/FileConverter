package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.responseHandling.ResponseMessage;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.ValidationException;

public interface FileConversion {

    String conversion(String data) throws ValidationException;

}




