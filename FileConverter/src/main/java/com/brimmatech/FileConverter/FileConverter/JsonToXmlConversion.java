package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.exception.ConversionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class JsonToXmlConversion implements FileConversion {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String conversion(String data){
        try {
            JsonNode jsonNode = objectMapper.readTree(data);

            String xmlConvert = xmlMapper.writeValueAsString(jsonNode);
            xmlConvert = xmlConvert.replace("\"", "");
            xmlConvert = xmlConvert.replace("<ObjectNode>", "").replace("</ObjectNode>", "");
            log.info("Converted Xml :{}", xmlConvert);
            return xmlConvert;
        }  catch (Exception e) {
            throw new ConversionException("Failed to convert JSON to XML. Unexpected error.", e);
        }

    }

}
